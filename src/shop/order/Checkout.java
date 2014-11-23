package shop.order;

import shop.discount.DiscountService;

import java.math.BigDecimal;

public class Checkout {
    private final Order order;
    private final DiscountService discountService;

    public Checkout(Order order, DiscountService discountService) {
        this.order = order;
        this.discountService = discountService;
    }

    public BigDecimal total() {
        BigDecimal total = order.getOrderTotal();

        return total;
    }
}
