package com.cs.zooicecreamparlour.service;

import com.cs.zooicecreamparlour.exception.BadQuantityInputException;
import com.cs.zooicecreamparlour.exception.WrongInputOrderException;
import com.cs.zooicecreamparlour.model.Order;

public interface OrderCalculatorService {
    void calculateTotalToPay(final Order order) throws WrongInputOrderException, BadQuantityInputException;
}
