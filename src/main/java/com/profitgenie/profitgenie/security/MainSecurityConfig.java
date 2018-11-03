package com.profitgenie.profitgenie.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
public class MainSecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationSuccessHandler loginHandler() {
        return new SuccessfulLoginHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable();

        http
                .authorizeRequests()                                                                                                                                                                                     //todo remove user/**
                .antMatchers("/", "/login/**", "/index.html", "/login.html", "/components/**", "/css/**", "/js/**", "/fonts/**", "/images/**", "/.sass-cache/**", "/services.html" ,"/user/changePassword**", "user/**").permitAll();

        http
                .formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/api/authentication")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(loginHandler())
                    .failureForwardUrl("/login.html");


        http
                .authorizeRequests()
                .antMatchers("/admin-page.html")
                .hasRole(SecurityConstants.SUPPORT);


        http
                .authorizeRequests()
                .antMatchers("/members-page.html")
                .hasRole(SecurityConstants.MEMBER);

        http
                .authorizeRequests()
                .antMatchers("/user/changePassword*", "update-password*")
                .hasAuthority("CHANGE_PASSWORD_PRIVILEGE");


        http.sessionManagement()
                .maximumSessions(1)
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .sessionFixation().none();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

//
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/user/**");
//    }
}

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//        http.sessionManagement()
//                .maximumSessions(1)
//                .expiredUrl("/login.html")
//                .and()
//                .invalidSessionUrl("/login.html");
//        http.sessionManagement()
//                .sessionFixation().migrateSession();
