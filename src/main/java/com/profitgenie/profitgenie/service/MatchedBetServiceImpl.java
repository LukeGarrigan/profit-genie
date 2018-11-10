package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.MatchedBet;
import com.profitgenie.profitgenie.dao.repository.MatchedBetDao;
import com.profitgenie.profitgenie.exceptions.InvalidURLException;
import com.profitgenie.profitgenie.exceptions.MustBeSupportToDeleteMatchedBet;
import com.profitgenie.profitgenie.rest.controller.dto.MatchedBetDto;
import com.profitgenie.profitgenie.security.SecurityConstants;
import com.profitgenie.profitgenie.security.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Service
public class MatchedBetServiceImpl implements MatchedBetService, DtoDomainConversion<MatchedBetDto, MatchedBet> {


    @Resource
    private MatchedBetDao matchedBetDao;

    @Resource
    private ModelMapper modelMapper;


    @Override
    public MatchedBetDto createMatchedBet(MatchedBetDto matchedBetDto) {

        MatchedBet matchedBet = new MatchedBet();

        checkValidURL(matchedBetDto.getAffiliateLink());
        matchedBet.setAffiliateLink(matchedBetDto.getAffiliateLink());
        matchedBet.setDescription(matchedBetDto.getDescription());
        matchedBet.setPathToImage(matchedBetDto.getPathToImage());
        matchedBet.setTitle(matchedBetDto.getTitle());
        matchedBet.setSequence(matchedBetDto.getSequence());
        matchedBet.setLinkLabel(matchedBetDto.getLinkLabel());
        matchedBet.setDate(new Date());
        incrementAllOtherSequences();

        matchedBetDao.save(matchedBet);

        // now has an id
        matchedBetDto.setId(matchedBet.getId());
        return matchedBetDto;
    }

    private void checkValidURL(String affiliateLink) {
        try {
            new URL(affiliateLink);
        } catch (MalformedURLException malformedURLException) {
            throw new InvalidURLException(affiliateLink);
        }
    }


    @Override
    public List<MatchedBetDto> getMatchedBets() {
        List<MatchedBet> allMatchedBets = matchedBetDao.findAll();

        List<MatchedBetDto> matchedBetDtos = new ArrayList<>();
        for (MatchedBet allMatchedBet : allMatchedBets) {
            MatchedBetDto matchedBetDto = toDto(allMatchedBet);
            matchedBetDto.setPathToImage("/resources/sky-bet.png");
            matchedBetDtos.add(matchedBetDto);
        }

        sortMatchedBets(matchedBetDtos);
        return matchedBetDtos;
    }

    @Override
    public List<MatchedBetDto> updateMatchedBets(List<MatchedBetDto> matchedBetDtos) {

        for (MatchedBet matchedBet : matchedBetDao.findAll()) {
            matchedBet.setSequence(getMatchedBetSequence(matchedBetDtos, matchedBet.getId()));
            matchedBetDao.save(matchedBet);
        }

        return matchedBetDtos;
    }

    @Override
    public void deleteMatchedBet(long id) {
        Collection<? extends GrantedAuthority> authorities = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
        if (!isASupport(authorities)) {
            throw new MustBeSupportToDeleteMatchedBet(id);
        } else {
            matchedBetDao.deleteById(id);
        }
    }

    private boolean isASupport(Collection<? extends GrantedAuthority> authorities) {
        boolean hasPermissions = false;
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_" + SecurityConstants.SUPPORT)) {
                hasPermissions = true;
            }
        }
        return hasPermissions;
    }

    private long getMatchedBetSequence(List<MatchedBetDto> matchedBetDtos, long id) {
        for (int i = 0; i < matchedBetDtos.size(); i++) {
            if (matchedBetDtos.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }


    private void sortMatchedBets(List<MatchedBetDto> matchedBetDtos) {
        matchedBetDtos.sort((first, second) -> {
            if (first.getSequence() == second.getSequence()) {
                return 0;
            } else if (first.getSequence() > second.getSequence()) {
                return 1;
            } else {
                return -1;
            }
        });
    }


    @Override
    public MatchedBetDto toDto(MatchedBet domain) {
        return modelMapper.map(domain, MatchedBetDto.class);
    }

    private void incrementAllOtherSequences() {
        for (MatchedBet matchedBet : matchedBetDao.findAll()) {
            matchedBet.setSequence(matchedBet.getSequence() + 1);
        }
    }


}


