package shop.order;

import shop.discount.DiscountService;
import shop.discount.voucher.VoucherCode;

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
        VoucherCode voucher = null;
        BigDecimal total;

        try {
            voucher = VoucherCode.valueOf(voucherCode);
            total = discountService.getDiscountedOrderWithVoucher(order, voucher).getOrderTotal();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid voucher code");
            System.out.println("Voucher has not been accepted");
            total = discountService.getDiscountedOrder(order).getOrderTotal();
        }

        return total;
    }
}
