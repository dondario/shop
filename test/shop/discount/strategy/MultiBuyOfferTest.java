package shop.discount.strategy;

import org.junit.Test;
import shop.discount.DiscountResult;
import shop.discount.DiscountResultMatcher;
import shop.order.Order;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

public class MultiBuyOfferTest {

    @Test
    public void getDiscountShouldReturnNullIfNoDiscountIsApplied() throws Exception {
        MultiBuyOffer underTest = new MultiBuyOffer(3, 2);

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("10")).build()).quantity(2).build())
        ).build();

        assertNull((underTest.getDiscountResult(order)));
    }

    @Test
    public void getDiscountShouldReturnDiscountForBuyThreeForPriceOfTwo() throws Exception {
        MultiBuyOffer underTest = new MultiBuyOffer(3, 2);

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("10")).build()).quantity(3).build())
        ).build();

        assertThat((underTest.getDiscountResult(order)), new DiscountResultMatcher(new DiscountResult(new BigDecimal("10"))));
    }

    @Test
    public void getDiscountResultShouldReturnDiscountForBuyNineGetThreeFree() {
        MultiBuyOffer underTest = new MultiBuyOffer(3, 2);

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("1")).build()).quantity(9).build())
        ).build();

        assertThat((underTest.getDiscountResult(order)), new DiscountResultMatcher(new DiscountResult(new BigDecimal("3"))));
    }

    @Test
    public void getDiscountResultShouldReturnDiscountForBuyFiveForThePriceOfThree() {
        MultiBuyOffer underTest = new MultiBuyOffer(5, 3);

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("1")).build()).quantity(5).build())
        ).build();


        assertThat((underTest.getDiscountResult(order)), new DiscountResultMatcher(new DiscountResult(new BigDecimal("2"))));
    }
}

