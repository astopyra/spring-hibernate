package com.kodilla.kodillajms.controller;

import com.kodilla.kodillajms.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    JmsTemplate jmsTemplate;

    @PostMapping
    public String submitOrder(@RequestBody Order order) {
        jmsTemplate.convertAndSend("order-queue", order);
        return "order sent to JMS queue:" + order.getOrderId();
    }

}
