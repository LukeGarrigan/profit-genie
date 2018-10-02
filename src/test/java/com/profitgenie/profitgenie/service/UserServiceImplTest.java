package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.MatchedBet;
import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.dao.repository.UserDao;
import com.profitgenie.profitgenie.rest.controller.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Resource
    private UserService userService;

    @MockBean
    private UserDao userDao;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;


    @TestConfiguration
    static class MatchedBetServiceConfiguration {

        @Bean
        public UserService getUserServiceImpl() {
            return new UserServiceImpl();
        }
    }

    @Test
    public void testIsSupportUser() {
        // given
        User user = new User();
        user.setEmail("luke.garrigan@gmail.com");
        user.setPassword("lkashdfhjklfg");
        user.setSupport(true);
        Mockito.when(userDao.getOne(-1L)).thenReturn(user);
        // when
        boolean userSupport = userService.isUserSupport(-1);
        // then
        assertEquals(true, userSupport);
    }



    @Test
    public void testNotSupportUser() {
        // given
        Mockito.when(userDao.getOne(-1L)).thenReturn(null);
        // when
        boolean userSupport = userService.isUserSupport(-1);
        // then
        assertEquals(false, userSupport);
    }



    @Test
    public void testSuccessfullyCreateUser(){
        // given
        UserDto userDto = new UserDto();
        userDto.setEmail("luke.garrigan@gmail.com");
        userDto.setPassword("lkashdfhjklfg");
        userDto.setSupport(true);
        Mockito.when(passwordEncoder.encode(userDto.getPassword())).thenReturn("potato");
        Mockito.when(userDao.save(Mockito.any())).thenReturn(new User());
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(userDto);
        // when
        UserDto userReturned = userService.createUser(userDto);
        // then
        assertEquals(userDto.getEmail(), userReturned.getEmail());

    }

}
