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

        matchedBetDao.save(matchedBet);

        return matchedBetDto;
    }

    @Override
    public List<MatchedBetDto> getMatchedBets() {
        List<MatchedBet> allMatchedBets = matchedBetDao.findAll();

        List<MatchedBetDto> matchedBetDtos = new ArrayList<>();
        for (MatchedBet allMatchedBet : allMatchedBets) {
            matchedBetDtos.add(toDto(allMatchedBet));
        }

        return matchedBetDtos;
    }

    @Override
    public MatchedBetDto toDto(MatchedBet domain) {
        return modelMapper.map(domain, MatchedBetDto.class);
    }
}


