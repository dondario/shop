package shop.order;

import shop.discount.DiscountResult;
import shop.discount.DiscountService;

import java.math.BigDecimal;
import java.util.List;

public class Checkout {
    private final Order order;
    private final DiscountService discountService;

    public Checkout(Order order, DiscountService discountService) {
        this.order = order;
        this.discountService = discountService;
    }

    public BigDecimal total() {
        BigDecimal total = order.getOrderTotal();

        for(DiscountResult discountResult : discountService.getDiscountResults(order)) {
            total = total.subtract(discountResult.getAmount());
        }

        return total;
    }
}
