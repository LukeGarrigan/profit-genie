package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.User;

import javax.persistence.*;


public class UserDaoCustomImpl implements UserDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;


    public User findUserByEmail(String email) {
        TypedQuery<User> userQuery = entityManager.createQuery("select e from User e where e.email = :email", User.class);
        userQuery.setParameter("email" , email);

        try {
            User singleResult = userQuery.getSingleResult();
            return singleResult;
        } catch (NoResultException exception) {
            return null;
        }
    }


}
