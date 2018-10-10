package com.profitgenie.profitgenie.security;

import com.profitgenie.profitgenie.exceptions.PasswordIncorrectException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderImpl extends BCryptPasswordEncoder {


    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (!super.matches(rawPassword, encodedPassword)) {
            throw new PasswordIncorrectException();
        }
        return true;
    }
}
