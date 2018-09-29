package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.rest.controller.dto.MatchedBetDto;

import java.util.List;

public interface MatchedBetService {


    MatchedBetDto createMatchedBet(MatchedBetDto matchedBetDto);

    List<MatchedBetDto> getMatchedBets();

    List<MatchedBetDto> updateMatchedBets(List<MatchedBetDto> matchedBetDtos);

    void deleteMatchedBet(long id);
}
