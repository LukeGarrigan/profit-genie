package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.MatchedBet;
import com.profitgenie.profitgenie.dao.repository.MatchedBetDao;
import com.profitgenie.profitgenie.rest.controller.dto.MatchedBetDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

        matchedBet.setAffiliateLink(matchedBetDto.getAffiliateLink());
        matchedBet.setDescription(matchedBetDto.getDescription());
        matchedBet.setPathToImage(matchedBetDto.getPathToImage());
        matchedBet.setTitle(matchedBetDto.getTitle());
        matchedBet.setSequence(matchedBetDto.getSequence());

        incrementAllOtherSequences();

        matchedBetDao.save(matchedBet);

        // now has an id
        matchedBetDto.setId(matchedBet.getId());
        return matchedBetDto;
    }


    @Override
    public List<MatchedBetDto> getMatchedBets() {
        List<MatchedBet> allMatchedBets = matchedBetDao.findAll();

        List<MatchedBetDto> matchedBetDtos = new ArrayList<>();
        for (MatchedBet allMatchedBet : allMatchedBets) {
            matchedBetDtos.add(toDto(allMatchedBet));
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
        matchedBetDao.deleteById(id);
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


