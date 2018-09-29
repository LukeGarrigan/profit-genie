package com.profitgenie.profitgenie.rest.controller;


import com.profitgenie.profitgenie.rest.controller.dto.MatchedBetDto;
import com.profitgenie.profitgenie.service.MatchedBetService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/matched")
public class MatchedBetController {


    @Resource
    private MatchedBetService matchedBetService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public MatchedBetDto createMatchedBet(@RequestBody MatchedBetDto matchedBetDto) {
        return matchedBetService.createMatchedBet(matchedBetDto);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<MatchedBetDto> getMatchedBets() {
        return matchedBetService.getMatchedBets();
    }


    @RequestMapping(value = "/updateMatchedBets", method = RequestMethod.POST)
    public List<MatchedBetDto> updateMatchedBets(@RequestBody List<MatchedBetDto> matchedBetDtos) {
        return matchedBetService.updateMatchedBets(matchedBetDtos);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteMatchedBet(@PathVariable String id) {
        matchedBetService.deleteMatchedBet(Long.parseLong(id));
    }

}
