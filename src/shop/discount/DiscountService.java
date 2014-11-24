package shop.discount;


import com.google.common.collect.Ordering;
import shop.discount.voucher.VoucherCode;
import shop.order.Order;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DiscountService {

    private final DiscountFactory discountFactory;
    private final List<Discount> availableDiscounts;

    public DiscountService(DiscountFactory discountFactory, List<Discount> availableDiscounts) {
        this.discountFactory = discountFactory;
        this.availableDiscounts = availableDiscounts;
    }

    public Order getDiscountedOrder(Order order) {
        List<DiscountResult> discountResults = newArrayList();
        Order discountedOrder = new Order(order.getOrderLines());

        Collections.sort(availableDiscounts, byPriority());
        for (Discount discount : availableDiscounts) {
            DiscountResult result = discount.apply(discountedOrder);
            if (null != result) {
                discountResults.add(result);
            }

            discountedOrder = new Order(order.getOrderLines(), discountResults);
        }

        return discountedOrder;
    }

    private Ordering<Discount> byPriority() {
        return new Ordering<Discount>() {
            @Override
            public int compare(Discount lhs, Discount rhs) {
                return lhs.getPriority() - rhs.getPriority();
            }
        };
    }

    public Order getDiscountedOrderWithVoucher(Order order, VoucherCode voucherCode) {
        Discount voucherDiscount = discountFactory.buildDiscount(voucherCode.toString());
        availableDiscounts.add(voucherDiscount);

        return getDiscountedOrder(order);
    }
}
