package shop;

import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Before;
import org.junit.Test;
import shop.discount.DiscountFactory;
import shop.discount.DiscountService;
import shop.order.Checkout;
import shop.order.Order;
import shop.product.ProductCode;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

public class ShopIntegrationTest {

    private DiscountFactory discountFactory;
    private DiscountService discountService;

    @Before
    public void setUp() throws Exception {
        discountFactory = new DiscountFactory();
        discountService = new DiscountService(discountFactory,
                newArrayList(
                        discountFactory.buildDiscount("PERCENT_10_FOR_50_POUND_SPEND"),
                        discountFactory.buildDiscount("MULTIBUY_3_FOR_2")));
    }

    @Test
    public void checkoutAndReceiveTenPercentOffForFiftyPoundSpend() throws Exception {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("100")).build()).quantity(1).build())
        ).build();

        Checkout checkout = new Checkout(order, discountService);

        assertThat(checkout.total(), new BigDecimalCloseTo(BigDecimal.valueOf(126), BigDecimal.valueOf(2)));
    }

    @Test
    public void checkoutAndReceiveThreeForThePriceOfTwo() throws Exception {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("1")).build()).quantity(3).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("10")).build()).quantity(1).build())
        ).build();

        Checkout checkout = new Checkout(order, discountService);

        assertThat(checkout.total(), new BigDecimalCloseTo(BigDecimal.valueOf(12), BigDecimal.valueOf(2)));
    }

    @Test
    public void checkoutAndReceiveThreeForThePriceOfTwoAndTenPercentOff() throws Exception {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("60")).build()).quantity(3).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("100")).build()).quantity(1).build())
        ).build();

        Checkout checkout = new Checkout(order, discountService);

        assertThat(checkout.total(), new BigDecimalCloseTo(BigDecimal.valueOf(198), BigDecimal.valueOf(2)));
    }

    @Test
    public void checkoutAndReceiveOneFreeWidgetWithVoucher() throws Exception {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Widget).cost(new BigDecimal("2")).build()).quantity(1).build(),
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Thingummy).cost(new BigDecimal("6")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().productCode(ProductCode.ShinyTech).cost(new BigDecimal("10")).build()).quantity(1).build())
        ).build();

        Checkout checkout = new Checkout(order, discountService);

        assertThat(checkout.totalWithCoupon("OneFreeWidget"), new BigDecimalCloseTo(BigDecimal.valueOf(22), BigDecimal.valueOf(2)));
    }

    @Test
    public void checkoutAndReceiveNothingWithInvalidVoucher() throws Exception {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Widget).cost(new BigDecimal("2")).build()).quantity(1).build(),
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Thingummy).cost(new BigDecimal("6")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().productCode(ProductCode.ShinyTech).cost(new BigDecimal("10")).build()).quantity(1).build())
        ).build();

        Checkout checkout = new Checkout(order, discountService);

        assertThat(checkout.totalWithCoupon("invalidVoucher"), new BigDecimalCloseTo(BigDecimal.valueOf(23), BigDecimal.valueOf(2)));
    }

    @Test
    public void checkoutAndReceiveOneFreeWidgetWithVoucherThreeForTwoAndTenPercentOff() throws Exception {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Widget).cost(new BigDecimal("29")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Thingummy).cost(new BigDecimal("60")).build()).quantity(3).build(),
                orderLineBuilder().product(productBuilder().productCode(ProductCode.ShinyTech).cost(new BigDecimal("100")).build()).quantity(1).build())
        ).build();

        Checkout checkout = new Checkout(order, discountService);

        assertThat(checkout.totalWithCoupon("OneFreeWidget"), new BigDecimalCloseTo(BigDecimal.valueOf(224.10), BigDecimal.valueOf(2)));
    }
}
