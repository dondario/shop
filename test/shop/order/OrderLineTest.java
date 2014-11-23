package shop.order;

import org.junit.Assert;
import org.junit.Test;
import shop.discount.DiscountResult;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

public class OrderLineTest {
    @Test
    public void shouldCalculateCostOfTwoProducts() {
        // Given
        OrderLine orderLine = orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build();

        // When
        BigDecimal total = orderLine.getTotal();

        // Then
        assertThat(total, is(new BigDecimal("40")));
    }

}
