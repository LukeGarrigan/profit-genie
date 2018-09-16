package com.profitgenie.profitgenie.service;


import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.dao.repository.UserDao;
import com.profitgenie.profitgenie.exceptions.EmailAlreadyRegistered;
import com.profitgenie.profitgenie.exceptions.PasswordIncorrectException;
import com.profitgenie.profitgenie.exceptions.UserNotFoundException;
import com.profitgenie.profitgenie.rest.controller.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, DtoDomainConversion<UserDto, User> {


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if (!userAlreadyExists(user)) {
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
    public UserDto loginUser(UserDto userDto) {

        if (!doesUserExist(userDto)) {
            throw new UserNotFoundException(userDto.getEmail());
        } else {
            for (User existingUser : getAllUsers()) {
                if (existingUser.getEmail().equals(userDto.getEmail())) {
                    if (passwordEncoder.matches(userDto.getPassword(), existingUser.getPassword())) {
                        return toDto(existingUser);
                    }
                }
            }
        }
        throw new PasswordIncorrectException(userDto.getEmail());
    }

    private boolean doesUserExist(UserDto userDto) {
        for (User existingUser : getAllUsers()) {
            if (existingUser.getEmail().equals(userDto.getEmail())) {
                return true;
            }
        }
        return false;
    }


    public boolean userAlreadyExists(User userToBeCreated) {
        for (User existingUser : getAllUsers()) {
            if (existingUser.getEmail().equals(userToBeCreated.getEmail())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public UserDto toDto(User domain) {
        return modelMapper.map(domain, UserDto.class);
    }
}
