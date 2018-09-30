package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.MatchedBet;
import com.profitgenie.profitgenie.dao.repository.MatchedBetDao;
import com.profitgenie.profitgenie.rest.controller.dto.MatchedBetDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
public class MatchedBetServiceImplTest {

    @MockBean
    private MatchedBetDao matchedBetDao;


    @MockBean
    private ModelMapper modelMapper;


    @Resource
    private MatchedBetService matchedBetService;

    private List<MatchedBet> matchedBetList = new ArrayList<>();


    @TestConfiguration
    static class MatchedBetServiceConfiguration {

        @Bean
        public MatchedBetService getMatchedBetServiceImpl() {
            return new MatchedBetServiceImpl();
        }
    }


    @Before
    public void setUp() {
        setMatchedBetList();
    }


    @Test
    public void testCreateNewMatchedBetSequence() {
        MatchedBetDto matchedBetDto = getMatchedBetDto();
        MatchedBetDto returnedMatchedBetDto = matchedBetService.createMatchedBet(matchedBetDto);
        assertEquals(matchedBetDto.getSequence(), returnedMatchedBetDto.getSequence());

    }

    @Test
    public void testCreateNewMatchedBetDescription() {
        MatchedBetDto matchedBetDto = getMatchedBetDto();
        MatchedBetDto returnedMatchedBetDto = matchedBetService.createMatchedBet(matchedBetDto);
        assertEquals(matchedBetDto.getDescription(), returnedMatchedBetDto.getDescription());

    }

    @Test
    public void testCreateNewMatchedBetAffiliateLink() {
        MatchedBetDto matchedBetDto = getMatchedBetDto();
        MatchedBetDto returnedMatchedBetDto = matchedBetService.createMatchedBet(matchedBetDto);
        assertEquals(matchedBetDto.getAffiliateLink(), returnedMatchedBetDto.getAffiliateLink());

    }

    @Test
    public void testOrderingOfMatchedBets() {
        MatchedBetDto matchedBetDto = getMatchedBetDto();
        Mockito.when(matchedBetDao.findAll()).thenReturn(matchedBetList);
        matchedBetService.createMatchedBet(matchedBetDto);

        for (int i = 0; i < matchedBetList.size(); i++) {
            assertEquals(i + 1, matchedBetList.get(i).getSequence());
        }
    }


    @Test
    public void testOrderingOfFetchingMatchedBets() {
        List<MatchedBet> reversedList = new ArrayList<>();

        for (int i = matchedBetList.size() - 1; i >= 0; i--) {
            reversedList.add(matchedBetList.get(i));
        }

        List<MatchedBetDto> matchedBetDtoList = new ArrayList<>();
        MatchedBetDto matchedBetDto = new MatchedBetDto();
        matchedBetDto.setSequence(reversedList.get(0).getSequence());
        matchedBetDtoList.add(matchedBetDto);

        MatchedBetDto matchedBetDto2 = new MatchedBetDto();
        matchedBetDto2.setSequence(reversedList.get(1).getSequence());
        matchedBetDtoList.add(matchedBetDto2);

        MatchedBetDto matchedBetDto3 = new MatchedBetDto();
        matchedBetDto3.setSequence(reversedList.get(2).getSequence());
        matchedBetDtoList.add(matchedBetDto3);


        for (int i = 0; i < reversedList.size(); i++) {
            MatchedBet matchedBet = reversedList.get(i);
            MatchedBetDto returnedDto = matchedBetDtoList.get(i);
            Mockito.when(modelMapper.map(matchedBet, MatchedBetDto.class)).thenReturn(returnedDto);
        }

        Mockito.when(matchedBetDao.findAll()).thenReturn(reversedList);


        List<MatchedBetDto> matchedBets = matchedBetService.getMatchedBets();
        for (int i = 0; i < matchedBets.size(); i++) {
            assertEquals(i, matchedBets.get(i).getSequence());
        }
    }


    private MatchedBetDto getMatchedBetDto() {
        MatchedBetDto matchedBetDto = new MatchedBetDto();
        matchedBetDto.setSequence(0);
        matchedBetDto.setAffiliateLink("google.com");
        matchedBetDto.setDescription("This is a description on how to make some moneys");
        return matchedBetDto;
    }


    private void setMatchedBetList() {
        MatchedBet matchedBet = new MatchedBet();
        matchedBet.setSequence(0);
        matchedBet.setAffiliateLink("google.com");
        matchedBet.setDescription("This is a description on how to make some moneys");
        matchedBetList.add(matchedBet);

        MatchedBet matchedBet2 = new MatchedBet();
        matchedBet2.setSequence(1);
        matchedBet2.setAffiliateLink("skybet.com");
        matchedBet2.setDescription("This is a description on how to make some moneys");
        matchedBetList.add(matchedBet2);

        MatchedBet matchedBet3 = new MatchedBet();
        matchedBet3.setSequence(2);
        matchedBet3.setAffiliateLink("bet365.com");
        matchedBet3.setDescription("This will make you £2302");
        matchedBetList.add(matchedBet3);

    }


}