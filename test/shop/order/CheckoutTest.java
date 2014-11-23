package shop.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import shop.discount.DiscountResult;
import shop.discount.DiscountService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutTest {
    @Mock
    DiscountService discountService;

    @Test
    public void shouldCalculateOrderTotal(){
        // Given
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("9")).build()).quantity(1).build())
        ).build();

        // When
        BigDecimal orderTotal = new Checkout(order, discountService).total();
        List<DiscountResult> discountResults = newArrayList();
        when(discountService.getDiscountResults(order)).thenReturn(discountResults);

        // Then
        assertThat(orderTotal, is(new BigDecimal("49")));
    }

    @Test
    public void shouldApplyDiscountResultsToOrderTotal(){
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("60")).build()).quantity(1).build())
        ).build();

        when(discountService.getDiscountResults(order)).thenReturn(newArrayList(new DiscountResult(new BigDecimal("10"))));

        BigDecimal orderTotal = new Checkout(order, discountService).total();

        assertThat(orderTotal, is(new BigDecimal("90")));
    }


}
