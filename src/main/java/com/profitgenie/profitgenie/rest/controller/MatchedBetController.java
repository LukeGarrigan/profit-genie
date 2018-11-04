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

    @PostMapping(value = "/create")
    public MatchedBetDto createMatchedBet(@RequestBody MatchedBetDto matchedBetDto) {
        return matchedBetService.createMatchedBet(matchedBetDto);
    }


    @GetMapping(value = "/get")
    public List<MatchedBetDto> getMatchedBets() {
        return matchedBetService.getMatchedBets();
    }


    @PostMapping(value = "/updateMatchedBets")
    public List<MatchedBetDto> updateMatchedBets(@RequestBody List<MatchedBetDto> matchedBetDtos) {
        return matchedBetService.updateMatchedBets(matchedBetDtos);
    }


    @DeleteMapping(value = "/delete/{id}")
    public void deleteMatchedBet(@PathVariable String id) {
        matchedBetService.deleteMatchedBet(Long.parseLong(id));
    }

}
