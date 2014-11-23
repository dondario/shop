package shop.discount.strategy;

import org.junit.Test;
import shop.discount.DiscountResult;
import shop.discount.DiscountResultMatcher;
import shop.order.Order;
import shop.product.ProductCode;

import java.math.BigDecimal;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

public class OneFreeProductTest {

    private OneFreeProduct underTest;

    @Test
    public void testGetDiscountResultShouldReturnNullIfNoMatches() throws Exception {
        underTest = new OneFreeProduct(ProductCode.Thingummy);

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Gadget).cost(new BigDecimal("20")).build()).quantity(2).build())).build();

        assertThat(underTest.getDiscountResult(order), is(nullValue()));
    }

    @Test
    public void testGetDiscountResultShouldReturnDiscountIfMatches() throws Exception {
        underTest = new OneFreeProduct(ProductCode.Thingummy);

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Thingummy).cost(new BigDecimal("20")).build()).quantity(1).build())).build();

        assertThat(underTest.getDiscountResult(order), new DiscountResultMatcher(new DiscountResult(new BigDecimal(20))));
    }

    @Test
    public void testGetDiscountResultShouldReturnOnlyOneDiscountIfMatches() throws Exception {
        underTest = new OneFreeProduct(ProductCode.Thingummy);

        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().productCode(ProductCode.Thingummy).cost(new BigDecimal("20")).build()).quantity(10).build())).build();

        assertThat(underTest.getDiscountResult(order), new DiscountResultMatcher(new DiscountResult(new BigDecimal(20))));
    }
}
