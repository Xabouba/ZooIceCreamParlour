package com.cs.zooicecreamparlour.helper;

import com.cs.zooicecreamparlour.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DataInitializer {
    /**
     * For this exercise, we are manually initializing the ice creams and the promos
     * For future improvement, the data could be store in SQL database
     * @return the initialized Order object with the quantity defined in the constant values
     */
    public static Order getOrder(final int rockyRoadQty, final int cookiesCreamQty, final int netflixChillQty) {
        final LocalDate todayLocalDate = LocalDate.now();

        final Promo twoGetOnePromo = new Promo(1, 3, 1.0, todayLocalDate, todayLocalDate);
        final Promo twoGetHalfOnePromo = new Promo(2, 3, .5, todayLocalDate, todayLocalDate);

        final IceCream rockyRoad = new IceCream(1, IceCreamType.ROCKY_ROAD, 8.0, twoGetOnePromo);
        final IceCream cookiesCream = new IceCream(2, IceCreamType.COOKIES_AND_CREAM, 10.0, twoGetHalfOnePromo);
        final IceCream netflixChill = new IceCream(3, IceCreamType.NETFLIX_AND_CHILL, 12.0, null);

        final OrderItem rockyRoadItem = new OrderItem(1, rockyRoad, rockyRoadQty);
        final OrderItem cookiesCreamItem = new OrderItem(2, cookiesCream, cookiesCreamQty);
        final OrderItem netflixChillItem = new OrderItem(3, netflixChill, netflixChillQty);
        final List<OrderItem> orderItemList = Arrays.asList(rockyRoadItem, cookiesCreamItem, netflixChillItem);

        return new Order(1, todayLocalDate, orderItemList);
    }
}
