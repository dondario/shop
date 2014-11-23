package shop.discount;

import org.junit.Before;
import org.junit.Test;
import shop.order.Order;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static shop.order.OrderBuilder.orderBuilder;
import static shop.order.OrderLineBuilder.orderLineBuilder;
import static shop.product.ProductBuilder.productBuilder;

public class DiscountServiceTest {

    private DiscountService underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new DiscountService();
    }

    @Test
    public void getDiscountResultsShouldNotReturn10PercentOffDiscountForOrdersUnder50Pounds() {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("9")).build()).quantity(1).build())
        ).build();

        assertThat(underTest.getDiscountResults(order), is(empty()));
    }

    @Test
    public void getDiscountResultsShouldReturn10PercentOffDiscountForOrdersOver50Pounds() {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("20")).build()).quantity(2).build(),
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("10")).build()).quantity(1).build())
        ).build();

        List<DiscountResult> actual = underTest.getDiscountResults(order);
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0), new DiscountResultMatcher(new DiscountResult(new BigDecimal("5"))));
    }

    @Test
    public void getDiscountResultShouldReturnDiscountForBuyThreeGetOneFree() {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("10")).build()).quantity(3).build())
        ).build();

        List<DiscountResult> actual = underTest.getDiscountResults(order);
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0), new DiscountResultMatcher(new DiscountResult(new BigDecimal("10"))));
    }

    @Test
    public void getDiscountResultShouldReturnDiscountForBuyNineGetThreeFree() {
        Order order = orderBuilder().orderLines(newArrayList(
                orderLineBuilder().product(productBuilder().cost(new BigDecimal("1")).build()).quantity(9).build())
        ).build();

        List<DiscountResult> actual = underTest.getDiscountResults(order);
        assertThat(actual.get(0), new DiscountResultMatcher(new DiscountResult(new BigDecimal("3"))));
    }
}
