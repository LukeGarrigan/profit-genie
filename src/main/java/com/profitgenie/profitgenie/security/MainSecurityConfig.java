package com.profitgenie.profitgenie.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class MainSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/index.html", "/components/**", "/css/**", "/js/**", "/fonts/**", "/images/**", "/.sass-cache/**", "/services.html").permitAll()
                .antMatchers("/user/login").permitAll()
                .anyRequest().authenticated()  // think this means if logged in then go anywhere
                .and()
            .formLogin()
                .loginPage("/login.html").permitAll()
                .and()
            .logout()
                .permitAll();
}
}
