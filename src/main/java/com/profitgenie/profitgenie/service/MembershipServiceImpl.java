package com.profitgenie.profitgenie.service;

import com.profitgenie.profitgenie.dao.domain.Order;
import com.profitgenie.profitgenie.dao.domain.User;
import com.profitgenie.profitgenie.dao.repository.OrderDao;
import com.profitgenie.profitgenie.dao.repository.UserDao;
import com.profitgenie.profitgenie.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class MembershipServiceImpl implements MembershipService {

    private static final Logger log = LoggerFactory.getLogger(MembershipServiceImpl.class);


    @Resource
    private UserService userService;

    @Resource
    private UserDao userDao;

    @Resource
    private OrderDao orderDao;


    @Override
    public void processNewMembership(Map<String, String> data) {
        log.debug("Processing membership");

        String email = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        String customerEmail = data.get("thrivecart[customer][email]");
        String accountName = data.get("thrivecart[account_name]");
        Integer orderId = Integer.valueOf(data.get("thrivecart[order_id]"));
        Integer accountId = Integer.valueOf(data.get("thrivecart[account_id]"));
        Integer orderTotal = Integer.valueOf(data.get("thrivecart[order_total]"));
        String orderName = data.get("thrivecart[order][0][n]");


        boolean areEmailsTheSame = areEmailsTheSame(email, customerEmail);

        if (areEmailsTheSame) {
            User user = userService.findUsersByEmail(email);
            user.setMember(true);
            userDao.save(user);

            Order order = new Order();
            order.setAccountName(accountName);
            order.setPaymentEmail(user.getEmail());
            order.setOrderId(orderId);
            order.setAccountId(accountId);
            order.setPrice(orderTotal);
            order.setOrderName(orderName);
            orderDao.save(order);
        }

    }

    private boolean areEmailsTheSame(String email, String customerEmail) {
        return email.equals(customerEmail);
    }
}
