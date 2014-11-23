package shop.discount;

import shop.discount.strategy.DiscountStrategy;
import shop.order.Order;

import java.util.List;

public class Discount {
//    private DiscountType type;
    private DiscountStrategy strategy;


    public Discount(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public DiscountResult apply(Order order) {
        return strategy.getDiscountResult(order);
    }
}
