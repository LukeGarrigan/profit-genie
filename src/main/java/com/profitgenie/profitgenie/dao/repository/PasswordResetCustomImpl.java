package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.PasswordResetToken;
import com.profitgenie.profitgenie.dao.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


public class PasswordResetCustomImpl implements PasswordRestCustom{

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public PasswordResetToken findByToken(String token) {
        TypedQuery<PasswordResetToken> userQuery = entityManager.createQuery("select e from PasswordResetToken e where e.token = :token", PasswordResetToken.class);
        userQuery.setParameter("token" , token);
        try {
            PasswordResetToken singleResult = userQuery.getSingleResult();
            return singleResult;
        } catch (NoResultException exception) {
            return null;
        }

    }


}
