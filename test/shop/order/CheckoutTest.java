package shop.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import shop.discount.DiscountResult;
import shop.discount.DiscountService;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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

        // Then
        assertThat(orderTotal, is(new BigDecimal("49")));
    }

    @Test
    public void checkoutShouldApplyDiscountsToOrder() throws Exception {

        Order originalOrder = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("9")).build()).quantity(1).build())
        ).build();

        Order newOrder = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("9")).build()).quantity(1).build())
        ).discountResult(new DiscountResult(BigDecimal.TEN)).build();

        // When
        discountService.getDiscountedOrder(originalOrder);
        BigDecimal orderTotal = new Checkout(newOrder, discountService).total();

        // Then
        assertThat(orderTotal, is(new BigDecimal("49")));
    }
}
