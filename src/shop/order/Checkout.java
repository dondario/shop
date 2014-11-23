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

        BigDecimal total = discountService.getDiscountedOrder(order).getOrderTotal();

        return total;
    }

    public BigDecimal totalWithCoupon(String voucherCode) {

        BigDecimal total = discountService.getDiscountedOrderWithVoucher(order, voucherCode).getOrderTotal();

        return total;
    }
}
