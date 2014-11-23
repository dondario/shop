package shop.order;

import shop.discount.DiscountResult;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class Order {
    private final List<OrderLine> orderLines;
    private final List<DiscountResult> discounts;

    public Order(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
        discounts = Collections.emptyList();
    }

    public Order(List<OrderLine> orderLines, List<DiscountResult> discounts) {
        this.orderLines = orderLines;
        this.discounts = discounts;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public List<DiscountResult> getDiscounts() {
        return discounts;
    }

    public BigDecimal getOrderTotal() {
        BigDecimal total = calculateOrderTotal().subtract(calculateDiscounts());

        return total;
    }

    private BigDecimal calculateDiscounts() {
        BigDecimal total = BigDecimal.ZERO;
        for(DiscountResult discountResult : discounts) {
            total = total.add(discountResult.getAmount());
        }
        return total;
    }

    private BigDecimal calculateOrderTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderLine orderLine : getOrderLines()) {
            total = total.add(orderLine.getTotal());
        }
        return total;
    }

}
