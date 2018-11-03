package com.profitgenie.profitgenie.service;


import com.profitgenie.profitgenie.dao.domain.PasswordResetToken;
import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.dao.repository.PasswordResetDao;
import com.profitgenie.profitgenie.dao.repository.UserDao;
import com.profitgenie.profitgenie.exceptions.EmailAlreadyRegistered;
import com.profitgenie.profitgenie.exceptions.MustBeSupportUser;
import com.profitgenie.profitgenie.exceptions.PasswordTooShortException;
import com.profitgenie.profitgenie.rest.controller.dto.UserDto;
import com.profitgenie.profitgenie.rest.controller.dto.UsersListDto;
import com.profitgenie.profitgenie.security.SecurityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, DtoDomainConversion<UserDto, User> {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetDao passwordResetDao;


    @Override
    public UserDto createUser(UserDto userDto) {

        User user = new User();
        user.setEmail(userDto.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setSupport(false);

        if (!userAlreadyExists(user.getEmail())) {
            userDao.save(user);
            return toDto(user);
        }
        throw new EmailAlreadyRegistered(userDto.getEmail());

    }

    @Override
    public User getUser(long id) {
        return userDao.getOne(id);
    }


    @Override
    public boolean isUserSupport(long id) {
        User user = userDao.getOne(id);
        return user.getSupport();
    }


    private boolean userAlreadyExists(String email) {

        if (userDao.findUserByEmail(email) != null) {
            return true;
        }
        return false;
    }

    @Override
    public User findUsersByEmail(String userEmail) {
        return userDao.findUserByEmail(userEmail);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);

        Calendar calendar =  Calendar.getInstance();
        calendar.setTime(calendar.getTime());
        calendar.add(Calendar.HOUR, 24);
        resetToken.setExpiryDate(calendar.getTime());
        passwordResetDao.save(resetToken);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);
    }

    @Override
    public UserDto toDto(User domain) {
        return modelMapper.map(domain, UserDto.class);
    }

    @Override
    public UsersListDto getUsers() {

        if (!isCurrentUserSupport()) {
            throw new MustBeSupportUser();
        }

        Map<String, Boolean> users = userDao.getUsers();
        UsersListDto usersListDto = new UsersListDto();
        usersListDto.setNames(users);
        return usersListDto;

    }

    private boolean isCurrentUserSupport() {
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {

            if (grantedAuthority.getAuthority() == SecurityConstants.ROLE + SecurityConstants.SUPPORT) {
                return true;
            }
        }
        return false;
    }
}
