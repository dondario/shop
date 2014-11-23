package shop.discount.strategy;

import shop.discount.DiscountResult;
import shop.order.Order;
import shop.order.OrderLine;
import shop.product.Product;
import shop.product.ProductCode;

import java.math.BigDecimal;
import java.util.List;

public class OneFreeProduct implements DiscountStrategy {
    ProductCode productCode;

    public OneFreeProduct(ProductCode productCode) {
        this.productCode = productCode;
    }

    @Override
    public DiscountResult getDiscountResult(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        List<OrderLine> orderLines = order.getOrderLines();
        for (OrderLine orderLine : orderLines) {
            Product product = orderLine.getProduct();

            if(productCode.equals(product.getProductCode())) {
               total = product.getCost();
               break;
            }
        }

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return new DiscountResult(total);
    }
}
