package shop.discount.strategy;

import shop.discount.DiscountResult;
import shop.order.Order;

public interface DiscountStrategy {

    DiscountResult getDiscountResult(Order order);
}
