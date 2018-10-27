package com.profitgenie.profitgenie.security;

import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.dao.repository.UserDao;
import com.profitgenie.profitgenie.exceptions.PasswordIncorrectException;
import com.profitgenie.profitgenie.exceptions.PasswordMustContainLettersAndNumbers;
import com.profitgenie.profitgenie.exceptions.PasswordTooShortException;
import com.profitgenie.profitgenie.service.SecurityServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, PasswordSecurityService {
    @Resource
    private HttpSession httpSession;

    @Resource
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {

        User userByEmail = userDao.findUserByEmail(user);

        if (userByEmail == null) {
            throw new PasswordIncorrectException();
        }

        try {
            UserDetailsImpl userDetails = new UserDetailsImpl(userByEmail, httpSession.getId());
            return userDetails;
        } catch (Exception e) {
            throw new PasswordIncorrectException();
        }

    }


    @Override
    public void checkPasswordComplexEnough(String password) {
        if (password.length() < 8) {
            throw new PasswordTooShortException();
        }

        if(!containsLetters(password) || !containNumbers(password)) {
            throw new PasswordMustContainLettersAndNumbers();
        }


    }


    private boolean containsLetters(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isAlphabetic(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean containNumbers(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
