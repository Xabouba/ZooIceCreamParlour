package com.cs.zooicecreamparlour.service;

import com.cs.zooicecreamparlour.exception.BadQuantityInputException;
import com.cs.zooicecreamparlour.exception.WrongInputOrderException;
import com.cs.zooicecreamparlour.model.Order;
import com.cs.zooicecreamparlour.model.OrderItem;
import com.cs.zooicecreamparlour.model.Promo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderCalculatorServiceImpl implements OrderCalculatorService {
    /**
     * Validate order input, calculate total orders and promo & set value of total to pay
     * @param order - Input order object
     * @throws WrongInputOrderException - throws wrong input order exception
     * @throws BadQuantityInputException - throws bad quantity input exception
     */
    public void calculateTotalToPay(final Order order) throws WrongInputOrderException, BadQuantityInputException {
        validateOrderInput(order);

        calculateTotalOrders(order);
        calculateTotalPromos(order);
        order.setTotalToPay(order.getTotalOrder() + order.getTotalPromos());
    }

    /**
     * Checks the order input for wrong input or incorrect quantity
     * @param order - Input order object
     * @throws WrongInputOrderException - throws wrong input order exception
     * @throws BadQuantityInputException - throws bad quantity input exception
     */
    private void validateOrderInput(Order order) throws WrongInputOrderException, BadQuantityInputException {
        if(order == null || order.getOrderItemList() == null || order.getOrderItemList().isEmpty()) {
            final String msg = "Order input is null or empty, please check and try again.";
            log.error(msg);
            throw new WrongInputOrderException(msg);
        }
        if(order.getOrderItemList().stream().anyMatch(x -> x.getQuantity() < 0)){
            final String msg = "Order input is incorrect, please check the quantity.";
            log.error(msg);
            throw new BadQuantityInputException(msg);
        }
    }

    /**
     * Calculate and set the totalPromos property of order
     * If promo is found and if promo is available for order date, we calculate the value and set as negative value
     * For example, order item quantity is 10 and unit price is 15
     * Promo available is: Buy 2 get 1 free, applyQuantity = 3 and discountQuantityOff = 1
     * For every 3 items, one is free, so totalPromo = 15 * ((10/3)*1) = 15 * 3 = 45
     *
     * @param order - Input order object
     */
    private void calculateTotalPromos(final Order order) {
        double promos = 0.0;

        for(final OrderItem item : order.getOrderItemList()){
            final Promo promo = validateAndGetPromo(order, item);
            if (promo == null) continue;

            int quantityToApply = item.getQuantity() / promo.getApplyQuantity();
            double promoSubTotal = item.getIceCream().getUnitPrice() * (quantityToApply*promo.getDiscountQuantityOff());
            promos += promoSubTotal;
        }

        order.setTotalPromos(-promos);
    }

    /**
     *
     * @param order current Order
     * @param item specific Item of the order
     * @return the available promo for the specific ice cream in item
     */
    private Promo validateAndGetPromo(Order order, OrderItem item) {
        if(item.getIceCream().getPromo() == null) {
            log.info("No promo found for {}", item.getIceCream().getIceCreamType().getDescription());
            return null;
        }
        final Promo promo = item.getIceCream().getPromo();

        if(promo.getStartDate().isAfter(order.getOrderDate()) || promo.getEndDate().isBefore(order.getOrderDate())) {
            log.info("The current promo for {} is not available for the order date",
                    item.getIceCream().getIceCreamType().getDescription());
            return null;
        }

        if(promo.getApplyQuantity() == 0){
            log.info("The current promo for {} is wrongly setup, the apply quantity cannot be 0",
                    item.getIceCream().getIceCreamType().getDescription());
            return null;
        }
        return promo;
    }

    /**
     * Calculate and set totalOrder value of order
     * @param order - Input order object
     */
    private void calculateTotalOrders(final Order order){
        double totalOrders = order.getOrderItemList().stream()
                .map(x -> x.getQuantity() * x.getIceCream().getUnitPrice()).reduce(0.0, Double::sum);
        order.setTotalOrder(totalOrders);
    }
}
