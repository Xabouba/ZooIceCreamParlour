package com.cs.zooicecreamparlour.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItem {
    private int id;
    private IceCream iceCream;
    private int quantity;
}
