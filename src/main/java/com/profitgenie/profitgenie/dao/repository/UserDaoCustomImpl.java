package com.profitgenie.profitgenie.dao.repository;

import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.rest.controller.dto.UserViewDto;

import javax.persistence.*;
import java.util.ArrayList;
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
    public List<UserViewDto> getUsers() {
        Query getUsersQuery = entityManager.createQuery("select aUser.email, anOrder.paymentEmail, aUser.member from User aUser left join Order anOrder on anOrder.user.id = aUser.id");
        try{
            List<Object[]> resultList = (List<Object[]>)getUsersQuery.getResultList();
            return buildUsersMap(resultList);
        } catch(NoResultException exception) {
            return null;
        }
    }

    private List<UserViewDto> buildUsersMap(List<Object[]> resultList) {
        List<UserViewDto> users = new ArrayList<>();
        for (Object[] objects : resultList) {
            UserViewDto userViewDto = new UserViewDto();

            String userEmail = (String)objects[0];
            String orderEmail = (String)objects[1];
            boolean isAMember = objects[2] == null ? false : (Boolean)objects[2];

            userViewDto.setEmail(userEmail);
            userViewDto.setOrderEmail(orderEmail);
            userViewDto.setMember(isAMember);

            users.add(userViewDto);

        }
        return users;
    }

}
