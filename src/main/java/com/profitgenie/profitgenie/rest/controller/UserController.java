package com.profitgenie.profitgenie.rest.controller;


import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.exceptions.NoCurrentSessionException;
import com.profitgenie.profitgenie.exceptions.UserNotFoundException;
import com.profitgenie.profitgenie.rest.controller.dto.EmailDto;
import com.profitgenie.profitgenie.rest.controller.dto.UserDto;
import com.profitgenie.profitgenie.security.PasswordSecurityService;
import com.profitgenie.profitgenie.service.EmailServiceImpl;
import com.profitgenie.profitgenie.service.MembershipService;
import com.profitgenie.profitgenie.service.SecurityService;
import com.profitgenie.profitgenie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private EmailServiceImpl mailSender;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordSecurityService passwordSecurityService;

    @Autowired
    private MembershipService membershipService;




    @PostMapping(value = "/email")
    public void sendEmail(@RequestBody EmailDto emailDto) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(emailDto.getSubject());
        email.setText(emailDto.getFirstName()+ " " + emailDto.getSecondName()+ "\n"+emailDto.getMessage());
        email.setTo("lukegarrigan8@gmail.com");
        email.setFrom(emailDto.getEmail());
        mailSender.sendSimpleMessage(email);
    }


    @PostMapping(value = "/create")
    public UserDto createUser(@RequestBody UserDto userDto, HttpServletRequest request, HttpServletResponse response) {
        passwordSecurityService.checkPasswordComplexEnough(userDto.getPassword());

        UserDto user = userService.createUser(userDto);
        authenticateUserAndSetSession(userDto, request);

        return user;
    }

    private void authenticateUserAndSetSession(UserDto user, HttpServletRequest request) {
        String username = user.getEmail();
        String password = user.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationProvider.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }


    @PostMapping(value = "/resetPassword")
    @ResponseBody
    public void resetPassword(HttpServletRequest request, @RequestBody String userEmail) {
        User user = userService.findUsersByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException(userEmail);
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.sendSimpleMessage(constructResetTokenEmail("localhost:5000", request.getLocale(), token, user));
    }

    private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
        String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        String message = "You have requested to reset your password";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom("profitgenie@gmail.com");
        return email;
    }

    @GetMapping(value = "/changePassword")
    public void showChangePasswordPage(HttpServletRequest request, HttpServletResponse response, Locale locale, @RequestParam("id") long id, @RequestParam("token") String token) throws IOException {
        String result = securityService.validatePasswordResetToken(id, token);
        if (result == null) {
            response.sendRedirect("/update-password.html");
        } else {
            response.sendRedirect("http://localhost:5000/login.html");
        }
    }


    @PostMapping(value = "/savePassword")
    public void savePassword(HttpServletRequest request, Locale locale, @RequestBody String password) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        passwordSecurityService.checkPasswordComplexEnough(password);

        if (user == null) {
            throw new NoCurrentSessionException();
        }
        userService.changeUserPassword(user, password);

    }

    @GetMapping(value = "/welcome")
    public void welcomeMember(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, String> data) throws IOException {
        membershipService.processNewMembership(data);
        response.sendRedirect("/members-page.html");
    }

}






