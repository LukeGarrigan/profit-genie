package com.profitgenie.profitgenie.security;

import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.dao.repository.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private HttpSession httpSession;


    @Resource
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User userByEmail = userDao.findUserByEmail(email);

        UserDetailsImpl userDetails = new UserDetailsImpl(userByEmail, httpSession.getId());

        return userDetails;
    }
}
