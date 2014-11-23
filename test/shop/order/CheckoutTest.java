package shop.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import shop.discount.DiscountResult;
import shop.discount.DiscountService;
import shop.product.ProductCode;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutTest {

    @Mock
    private DiscountService discountService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldDelegateOrderToDiscountService() {
        // Given
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("9")).build()).quantity(1).build())
        ).build();

        // When
        when(discountService.getDiscountedOrder(order)).thenReturn(order);
        BigDecimal orderTotal = new Checkout(order, discountService).total();

        // Then
        assertThat(orderTotal, is(new BigDecimal("49")));
    }

    @Test
    public void shouldGetTotalWithVoucherApplied() throws Exception {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Thingummy).cost(new BigDecimal("10"))
                        .build()).quantity(1).build()))
                .discountResult(new DiscountResult(BigDecimal.TEN)).build();

        String voucherCode = "voucher-code";
        when(discountService.getDiscountedOrderWithVoucher(any(Order.class), eq(voucherCode))).thenReturn(order);
        BigDecimal orderTotal = new Checkout(order, discountService).totalWithCoupon(voucherCode);

        assertThat(orderTotal, is(BigDecimal.ZERO));
    }
}
