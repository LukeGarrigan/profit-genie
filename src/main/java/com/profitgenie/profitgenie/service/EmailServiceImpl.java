package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;


    public void sendSimpleMessage(SimpleMailMessage simpleMailMessage) {
        emailSender.send(simpleMailMessage);
    }

}
