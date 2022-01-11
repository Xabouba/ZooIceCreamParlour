package com.cs.zooicecreamparlour;

import com.cs.zooicecreamparlour.exception.BadQuantityInputException;
import com.cs.zooicecreamparlour.exception.WrongInputOrderException;
import com.cs.zooicecreamparlour.helper.DataInitializer;
import com.cs.zooicecreamparlour.model.Order;
import com.cs.zooicecreamparlour.model.OrderItem;
import com.cs.zooicecreamparlour.service.OrderCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ZooIceCreamParlourApplication implements CommandLineRunner {
    private static final int ROCKY_ROAD_QTY = 1;
    private static final int COOKIES_CREAM_QTY = 3;
    private static final int NETFLIX_CHILL_QTY = 2;

    final OrderCalculatorService orderCalculatorService;

    @Autowired
    public ZooIceCreamParlourApplication(final OrderCalculatorService orderCalculatorService) {
        this.orderCalculatorService = orderCalculatorService;
    }

    public static void main(String[] args) {
        log.info("Starting ZooIceCreamParlourApplication");
        SpringApplication.run(ZooIceCreamParlourApplication.class, args);
        log.info("Ending ZooIceCreamParlourApplication");
    }

    @Override
    public void run(String... args) {
        final Order order = DataInitializer.getOrder(ROCKY_ROAD_QTY, COOKIES_CREAM_QTY, NETFLIX_CHILL_QTY);
        displayOrderItems(order);
        try {
            orderCalculatorService.calculateTotalToPay(order);
        } catch (WrongInputOrderException | BadQuantityInputException e) {
            e.printStackTrace();
        }
        displayOrderTotals(order);
    }

    private void displayOrderItems(Order order) {
        System.out.println("Order: ");
        for(OrderItem orderItem : order.getOrderItemList()){
            System.out.printf("Ice Cream : %s - Quantity %s%n",
                    orderItem.getIceCream().getIceCreamType().getDescription(), orderItem.getQuantity());
        }
    }

    private void displayOrderTotals(Order order) {
        System.out.printf("Total orders: %s%n", order.getTotalOrder());
        System.out.printf("Total promos: %s%n", order.getTotalPromos());
        System.out.printf("Total to pay: %s%n", order.getTotalToPay());
    }
}
