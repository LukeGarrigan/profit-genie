package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.User;

import java.util.Map;

public interface MembershipService {
    void processNewMembership(Map<String, String> data);
}
