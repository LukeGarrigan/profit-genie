package com.profitgenie.profitgenie.service;

public interface SecurityService {


    String validatePasswordResetToken(long id, String token);


}
