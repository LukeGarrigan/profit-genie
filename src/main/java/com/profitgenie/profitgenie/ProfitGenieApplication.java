package com.profitgenie.profitgenie;

import com.profitgenie.profitgenie.service.EmailServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProfitGenieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfitGenieApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl();
    }
}
