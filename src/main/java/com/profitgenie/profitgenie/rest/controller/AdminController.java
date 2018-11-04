package com.profitgenie.profitgenie.rest.controller;


import com.profitgenie.profitgenie.rest.controller.dto.UsersListDto;
import com.profitgenie.profitgenie.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Resource
    UserService userService;

    @RequestMapping(value = "/get-users", method = RequestMethod.GET)
    public UsersListDto getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/toggle", method = RequestMethod.POST)
    public UsersListDto createUser(@RequestBody String email) {
        userService.changeMembershipStatus(email);
        return userService.getUsers();
    }


}
