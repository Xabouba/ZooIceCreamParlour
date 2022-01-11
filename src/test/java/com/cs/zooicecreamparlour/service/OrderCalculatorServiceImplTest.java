package com.cs.zooicecreamparlour.service;

import com.cs.zooicecreamparlour.exception.BadQuantityInputException;
import com.cs.zooicecreamparlour.exception.WrongInputOrderException;
import com.cs.zooicecreamparlour.helper.DataInitializer;
import com.cs.zooicecreamparlour.model.IceCream;
import com.cs.zooicecreamparlour.model.IceCreamType;
import com.cs.zooicecreamparlour.model.Order;
import com.cs.zooicecreamparlour.model.OrderItem;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderCalculatorServiceImplTest {
    OrderCalculatorService orderCalculatorService;

    @Before
    public void setUp() {
        orderCalculatorService = new OrderCalculatorServiceImpl();
    }

    @Test
    public void testCalculateTotalToPayTodayOrder() throws WrongInputOrderException, BadQuantityInputException {
        final Order order = DataInitializer.getOrder(1,3,2);
        orderCalculatorService.calculateTotalToPay(order);

        assertEquals(57.0, order.getTotalToPay(), 0.0);
    }

    @Test
    public void testCalculateTotalToPayAllQuantityZero() throws WrongInputOrderException, BadQuantityInputException {
        final Order order = DataInitializer.getOrder(0,0,0);
        orderCalculatorService.calculateTotalToPay(order);

        assertEquals(0.0, order.getTotalToPay(), 0.0);
    }

    @Test
    public void testCalculateTotalToPayWith10OfEach() throws WrongInputOrderException, BadQuantityInputException {
        final Order order = DataInitializer.getOrder(10,10,10);
        orderCalculatorService.calculateTotalToPay(order);

        assertEquals(261.0, order.getTotalToPay(), 0.0);
    }

    @Test
    public void testCalculateTotalToPayWithNoPromo() throws WrongInputOrderException, BadQuantityInputException {
        final Order order = DataInitializer.getOrder(10,10,10);
        order.getOrderItemList().forEach(x -> x.getIceCream().setPromo(null));
        orderCalculatorService.calculateTotalToPay(order);

        assertEquals(300.0, order.getTotalToPay(), 0.0);
    }

    @Test
    public void testCalculateTotalToPayWithWrongSetupPromo() throws WrongInputOrderException, BadQuantityInputException {
        final Order order = DataInitializer.getOrder(10,10,10);
        order.getOrderItemList().forEach(x -> {if(x.getIceCream().getPromo() != null) {x.getIceCream().getPromo().setApplyQuantity(0);}});
        orderCalculatorService.calculateTotalToPay(order);

        assertEquals(300.0, order.getTotalToPay(), 0.0);
    }

    @Test
    public void testCalculateTotalToPayWithNoPromoAvailable() throws WrongInputOrderException, BadQuantityInputException {
        final Order order = DataInitializer.getOrder(10,10,10);
        order.setOrderDate(LocalDate.EPOCH);
        orderCalculatorService.calculateTotalToPay(order);

        assertEquals(300.0, order.getTotalToPay(), 0.0);
    }

    @Test(expected = WrongInputOrderException.class)
    public void testCalculateTotalToPayNullInput() throws WrongInputOrderException, BadQuantityInputException {
        orderCalculatorService.calculateTotalToPay(null);
    }

    @Test(expected = WrongInputOrderException.class)
    public void testCalculateTotalToPayNullListInput() throws WrongInputOrderException, BadQuantityInputException {
        final Order order = new Order();
        orderCalculatorService.calculateTotalToPay(order);
    }

    @Test(expected = WrongInputOrderException.class)
    public void testCalculateTotalToPayEmptyListInput() throws WrongInputOrderException, BadQuantityInputException {
        final Order order = new Order();
        order.setOrderItemList(new ArrayList<>());
        orderCalculatorService.calculateTotalToPay(order);
    }

    @Test(expected = BadQuantityInputException.class)
    public void testCalculateTotalToPayBelowZeroQuantity() throws WrongInputOrderException, BadQuantityInputException {
        final Order order = new Order();
        final OrderItem orderItem = new OrderItem(1, new IceCream(1, IceCreamType.COOKIES_AND_CREAM, 10.0, null),-10);
        order.setOrderItemList(List.of(orderItem));

        orderCalculatorService.calculateTotalToPay(order);
    }
}