package com.kodilla.kodillajms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Order implements Serializable {

    private int orderId;
    private String productName;
    private int quantity;

}
