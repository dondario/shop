package shop.discount;

import shop.discount.strategy.MultiBuyOffer;
import shop.discount.strategy.OneFreeProduct;
import shop.discount.strategy.PercentageOffSpendDiscount;
import shop.product.ProductCode;

import java.math.BigDecimal;

public class DiscountFactory {
    public Discount buildDiscount(String discountCode) {

        if (discountCode.equals("PERCENT_10_FOR_50_POUND_SPEND")) {
            return new Discount(new PercentageOffSpendDiscount(10, BigDecimal.valueOf(50)), 100);
        }

        if (discountCode.equals("MULTIBUY_3_FOR_2")) {
            return new Discount(new MultiBuyOffer(3, 2), 1);
        }

        if (discountCode.equals("OneFreeWidget")) {
            return new Discount(new OneFreeProduct(ProductCode.Widget), 3);
        }



        throw new IllegalArgumentException("could not create a discount.");
    }
}
