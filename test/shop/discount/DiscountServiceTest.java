package shop.discount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import shop.discount.strategy.MultiBuyOffer;
import shop.discount.strategy.OneFreeProduct;
import shop.discount.strategy.PercentageOffSpendDiscount;
import shop.order.Order;
import shop.product.ProductCode;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

@RunWith(MockitoJUnitRunner.class)
public class DiscountServiceTest {

    public static final Discount TEN_PERCENT_OFF_FIFTY_SPEND = new Discount(new PercentageOffSpendDiscount(10, new BigDecimal(50)), 100);
    public static final Discount THREE_FOR_TWO = new Discount(new MultiBuyOffer(3, 2), 1);
    public static final Discount ONE_FREE_THINGY = new Discount(new OneFreeProduct(ProductCode.Thingummy), 2);
    private DiscountService underTest;
    @Mock
    private DiscountFactory discountFactory;

    @Before
    public void setUp() throws Exception {

        underTest = new DiscountService(discountFactory, newArrayList(TEN_PERCENT_OFF_FIFTY_SPEND, THREE_FOR_TWO));
    }


    @Test
    public void getDiscountShouldApplyDiscountsOnDiscountedOrderTotals() throws Exception {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(3).build())
        ).build();

        List<DiscountResult> actual = underTest.getDiscountedOrder(order).getDiscounts();
        assertThat(actual.get(0), new DiscountResultMatcher(new DiscountResult(new BigDecimal("20"))));
    }

    @Test
    public void getDiscountShouldApplyInPriorityOrder() throws Exception {

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("100")).build()).quantity(3).build())
        ).build();

        List<DiscountResult> actual = underTest.getDiscountedOrder(order).getDiscounts();
        assertThat(actual.size(), equalTo(2));
        assertThat(actual.get(0), new DiscountResultMatcher(new DiscountResult(new BigDecimal("100"))));
        assertThat(actual.get(1), new DiscountResultMatcher(new DiscountResult(new BigDecimal("20"))));
    }

    @Test
    public void getDiscountedOrderWithVoucherShouldDelegateToFactoryToGetDiscount() throws Exception {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Gadget).cost(new BigDecimal("100")).build()).quantity(3).build(),
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Thingummy).cost(new BigDecimal("20")).build()).quantity(2).build())
        ).build();

        when(discountFactory.buildDiscount("voucherCode")).thenReturn(ONE_FREE_THINGY);
        List<DiscountResult> actual = underTest.getDiscountedOrderWithVoucher(order, "voucherCode").getDiscounts();

        assertThat(actual.size(), equalTo(3));
        assertThat(actual.get(0), new DiscountResultMatcher(new DiscountResult(new BigDecimal("100"))));
        assertThat(actual.get(1), new DiscountResultMatcher(new DiscountResult(new BigDecimal("20"))));
        assertThat(actual.get(2), new DiscountResultMatcher(new DiscountResult(new BigDecimal("22"))));
    }
}
