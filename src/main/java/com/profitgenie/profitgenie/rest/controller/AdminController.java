package com.profitgenie.profitgenie.rest.controller;


import com.profitgenie.profitgenie.rest.controller.dto.UsersListDto;
import com.profitgenie.profitgenie.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Resource
    UserService userService;

    @GetMapping(value = "/get-users")
    public UsersListDto getUsers() {
        return userService.getUsers();
    }

    @PostMapping(value = "/toggle")
    public UsersListDto createUser(@RequestBody String email) {
        userService.changeMembershipStatus(email);
        return userService.getUsers();
    }

}
