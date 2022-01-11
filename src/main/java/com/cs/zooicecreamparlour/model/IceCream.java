package com.cs.zooicecreamparlour.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IceCream {
    private int id;
    private IceCreamType iceCreamType;
    private double unitPrice;
    private Promo promo;
}
