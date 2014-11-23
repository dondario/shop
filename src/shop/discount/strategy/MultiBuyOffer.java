package shop.discount.strategy;

import shop.discount.DiscountResult;
import shop.order.Order;
import shop.order.OrderLine;

import java.math.BigDecimal;
import java.util.List;

public class MultiBuyOffer implements DiscountStrategy {
    Integer quantity;
    Integer forThePriceOf;

    public MultiBuyOffer(Integer quantity, Integer forThePriceOf) {
        this.quantity = quantity;
        this.forThePriceOf = forThePriceOf;
    }

    @Override
    public DiscountResult getDiscountResult(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        List<OrderLine> orderLines = order.getOrderLines();
        for (OrderLine orderLine : orderLines) {
            if (orderLine.getQuantity() >= quantity) {
                BigDecimal numberOfMultiBuys = new BigDecimal(Math.floor(orderLine.getQuantity() / quantity));
                Integer numberOfFreeItems = quantity - forThePriceOf;
                total = total.add(numberOfMultiBuys.multiply(new BigDecimal(numberOfFreeItems)
                     .multiply(orderLine.getProduct().getCost())));
            }
        }

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return new DiscountResult(total);
    }
}
