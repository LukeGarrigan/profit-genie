package com.profitgenie.profitgenie.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
public class MainSecurityConfig extends WebSecurityConfigurerAdapter {




    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login.html")
                .and()
                .invalidSessionUrl("/login.html");
        http.sessionManagement()
                .sessionFixation().migrateSession();
        http
            .authorizeRequests()
                .antMatchers("/", "/index.html", "/components/**", "/css/**", "/js/**", "/fonts/**", "/images/**", "/.sass-cache/**", "/services.html").permitAll()
                .anyRequest().authenticated()  // think this means if logged in then go anywhere
                .and()
            .formLogin()
                .loginPage("/login.html").permitAll()
                .and()
            .logout()
                .permitAll();

    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/user/**");
    }
}
