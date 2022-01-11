package com.cs.zooicecreamparlour.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Order {
    private int id;
    private LocalDate orderDate;
    private List<OrderItem> orderItemList;
    private double totalOrder;
    private double totalPromos;
    private double totalToPay;

    public Order(final int id, final LocalDate orderDate, final List<OrderItem> orderItemList) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderItemList = orderItemList;
    }
}
