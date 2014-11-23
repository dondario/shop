package shop.order;

import org.junit.Assert;
import org.junit.Test;
import shop.discount.DiscountResult;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

public class OrderTest {

    @Test
    public void shouldCalculateOrderTotal(){
        // Given
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("9")).build()).quantity(1).build())
        ).build();

        // When
        BigDecimal orderTotal = order.getOrderTotal();

        // Then
        Assert.assertThat(orderTotal, is(new BigDecimal("49")));
    }

    @Test
    public void shouldApplyDiscountResultsToOrderTotal(){
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("60")).build()).quantity(1).build())
        ).discountResult(new DiscountResult(new BigDecimal(10)))
                .build();

        assertThat(order.getOrderTotal(), is(new BigDecimal("90")));
    }
}
