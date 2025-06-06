package com.kodilla.kodillajms.receiver;

import com.kodilla.kodillajms.model.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderReceiver {

    @JmsListener(destination =  "order-queue", containerFactory = "jmsFactory")
    public void receiveOrder(Order order) {
        System.out.println("Received order: " + order);
    }
}
