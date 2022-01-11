package com.cs.zooicecreamparlour.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Promo {
    private int id;
    private int applyQuantity;
    private double discountQuantityOff;
    private LocalDate startDate;
    private LocalDate endDate;
}
