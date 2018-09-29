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

@Service
public class UserServiceImpl implements UserService, DtoDomainConversion<UserDto, User> {


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) {

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

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
    public UserDto loginUser(UserDto userDto) {

        User existingUser = userDao.findUserByEmail(userDto.getEmail());

        if (existingUser == null) {
            throw new UserNotFoundException(userDto.getEmail());
        } else {
            if (passwordEncoder.matches(userDto.getPassword(), existingUser.getPassword())) {
                return toDto(existingUser);
            } else {
                throw new PasswordIncorrectException(userDto.getEmail());
            }
        }
    }


    private boolean userAlreadyExists(String email) {

        if (userDao.findUserByEmail(email) != null) {
            return true;
        }
        return false;
    }


    @Override
    public UserDto toDto(User domain) {
        return modelMapper.map(domain, UserDto.class);
    }
}
