package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.PasswordResetToken;
import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.dao.repository.PasswordResetDao;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Calendar;

@Service
public class SecurityServiceImpl implements SecurityService {


    @Resource
    PasswordResetDao passwordResetDao;

    @Override
    public String validatePasswordResetToken(long id, String token) {
        PasswordResetToken passToken = passwordResetDao.findByToken(token);
        if ((passToken == null) || (passToken.getUser()
                .getId() != id)) {
            return "invalidToken";
        }

        Calendar calendar = Calendar.getInstance();
        if ((passToken.getExpiryDate()
                .getTime() - calendar.getTime()
                .getTime()) <= 0) {
            return "expired";
        }

        User user = passToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}
