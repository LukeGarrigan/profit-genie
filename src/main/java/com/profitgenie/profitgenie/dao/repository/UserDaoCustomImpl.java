package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.User;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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


    @Override
    public Map<String, Boolean> getUsers() {
        Query getUsersQuery = entityManager.createQuery("select aUser.email, anOrder.paymentEmail, aUser.member from User aUser left join Order anOrder on anOrder.user.id = aUser.id");
        try{
            List<Object[]> resultList = (List<Object[]>)getUsersQuery.getResultList();
            return buildUsersMap(resultList);
        } catch(NoResultException exception) {
            return null;
        }
    }

    private Map<String, Boolean> buildUsersMap(List<Object[]> resultList) {
        Map<String, Boolean> users = new HashMap<>();
        for (Object[] objects : resultList) {
            String userEmail = (String)objects[0];
            String orderEmail = (String)objects[1];

            StringBuilder emailCombined = new StringBuilder();
            emailCombined.append(userEmail);
            if (orderEmail != null) {
                emailCombined.append(" ("+orderEmail+ ") ");
            }

            boolean isAMember = objects[2] == null ? false : (Boolean)objects[2];
            users.put(emailCombined.toString(), isAMember);
        }
        return users;
    }

}
