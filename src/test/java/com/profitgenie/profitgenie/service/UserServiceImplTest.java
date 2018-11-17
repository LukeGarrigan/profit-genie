package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.MatchedBet;
import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.dao.repository.PasswordResetDao;
import com.profitgenie.profitgenie.dao.repository.UserDao;
import com.profitgenie.profitgenie.rest.controller.dto.UserDto;
import com.profitgenie.profitgenie.rest.controller.dto.UsersListDto;
import com.profitgenie.profitgenie.security.UserHasBecomeMember;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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

    @MockBean
    private PasswordResetDao passwordResetDao;


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
        assertTrue(userSupport);
    }



    @Test
    public void testIsMember() {
        // given
        User user = new User();
        user.setId(-1);
        user.setEmail("imamember@gmail.com");
        user.setMember(true);
        Mockito.when(userDao.getOne(-1L)).thenReturn(user);

        // when
        User returnedUser = userService.getUser(-1);

        // then
        assertEquals(true, returnedUser.getMember());
    }


    @Test
    public void testSuccessfullyChangeMembershipStatus() {
        // given
        User user = new User();
        user.setEmail("iwasamember@gmail.com");
        user.setSupport(false);
        user.setMember(true);
        Mockito.when(userDao.findUserByEmail("iwasamember@gmail.com")).thenReturn(user);

        // when
        userService.changeMembershipStatus(user.getEmail());

        // then
        assertEquals(false, user.getMember());
    }


    @Test
    public void testSuccessfullyChangeMembershipStatusToNotMember() {
        // given
        User user = new User();
        user.setEmail("iwasntamember@gmail.com");
        user.setSupport(false);
        user.setMember(false);
        Mockito.when(userDao.findUserByEmail("iwasntamember@gmail.com")).thenReturn(user);

        // when
        userService.changeMembershipStatus(user.getEmail());

        // then
        assertEquals(true, user.getMember());
    }


    @Test
    public void testSuccessfullyChangeUsersPassword() {
        // given

        String newPassword = "mynewpassword";
        User user = new User();
        user.setEmail("changingmypassword@gmail.com");
        user.setSupport(false);
        user.setMember(false);
        user.setPassword("somedodgyhashing");
        Mockito.when(passwordEncoder.encode(newPassword )).thenReturn("realgoodhashing");
        // when
        userService.changeUserPassword(user, newPassword);

        // then
        assertEquals("realgoodhashing", user.getPassword());
    }




    @Test
    public void testNotSupportUser() {
        // given
        User user = new User();
        user.setSupport(false);
        user.setEmail("blar@blar.com");
        user.setId(-1);

        Mockito.when(userDao.getOne(-1L)).thenReturn(user);
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
