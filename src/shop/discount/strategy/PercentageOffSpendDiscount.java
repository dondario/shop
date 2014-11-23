package shop.discount.strategy;

import shop.discount.DiscountResult;
import shop.order.Order;

import java.math.BigDecimal;

public class PercentageOffSpendDiscount implements DiscountStrategy {
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private final Integer percentage;
    private final BigDecimal minimumSpend;


    public PercentageOffSpendDiscount(Integer percentage, BigDecimal minimumSpend) {
        this.percentage = percentage;
        this.minimumSpend = minimumSpend;
    }

    @Override
    public DiscountResult getDiscountResult(Order order) {

        if (order.getOrderTotal().compareTo(minimumSpend) < 0) {
            return null;
        }

        return new DiscountResult(order.getOrderTotal().multiply(new BigDecimal(percentage).divide(ONE_HUNDRED)));
    }
}
