package shop.discount;

import shop.discount.strategy.DiscountStrategy;
import shop.order.Order;

import java.util.List;

public class Discount {
    private Integer priority;
    private DiscountStrategy strategy;

    public Discount(DiscountStrategy strategy, Integer priority) {
        this.priority = priority;
        this.strategy = strategy;
    }

    public DiscountResult apply(Order order) {
        return strategy.getDiscountResult(order);
    }

    public Integer getPriority() {
        return priority;
    }

    public DiscountStrategy getStrategy() {
        return strategy;
    }
}
