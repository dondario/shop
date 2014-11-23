package shop.discount.strategy;

import org.junit.Test;
import shop.discount.DiscountResult;
import shop.discount.DiscountResultMatcher;
import shop.order.Order;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

public class PercentageOffSpendDiscountTest {
    private PercentageOffSpendDiscount underTest;

    @Test
    public void getDiscountResultsShouldReturnEmptyListIfUnderMinimumSpend() {
        underTest = new PercentageOffSpendDiscount(10, new BigDecimal(50));

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("9")).build()).quantity(1).build())
        ).build();

        assertThat(underTest.getDiscountResult(order), is(nullValue()));
    }

    @Test
    public void getDiscountResultsShouldReturn10PercentOffDiscountForOrdersOver50Pounds() {
        underTest = new PercentageOffSpendDiscount(10, new BigDecimal(50));

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("10")).build()).quantity(1).build())
        ).build();

        assertThat(underTest.getDiscountResult(order), new DiscountResultMatcher(new DiscountResult(new BigDecimal("5"))));
    }
}
