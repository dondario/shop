package shop.order;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private final List<OrderLine> orderLines;

    public Order(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public BigDecimal getOrderTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderLine orderLine : getOrderLines()) {
            total = total.add(orderLine.getTotal());
        }

        return total;
    }

}
