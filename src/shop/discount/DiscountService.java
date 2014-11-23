package shop.discount;


import shop.discount.strategy.MultiBuyOffer;
import shop.discount.strategy.PercentageOffSpendDiscount;
import shop.order.Order;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DiscountService {

    public Order getDiscountedOrder(Order order) {
        List<DiscountResult> discountResults = newArrayList();
        List<Discount> discounts = newArrayList();

        Discount tenPercentDiscountOnSpendOverFifty = new Discount(new PercentageOffSpendDiscount(10, new BigDecimal(50)));
        Discount threeForThePriceOfTwo = new Discount(new MultiBuyOffer(3, 2));

        discounts.add(threeForThePriceOfTwo);
        discounts.add(tenPercentDiscountOnSpendOverFifty);


        Order discountedOrder = new Order(order.getOrderLines());
        for(Discount discount : discounts) {
            DiscountResult result = discount.apply(discountedOrder);
            if (null != result) {
                discountResults.add(result);
            }

            discountedOrder =  new Order(order.getOrderLines(), discountResults);
        }

        return discountedOrder;
    }
}
