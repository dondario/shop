package shop.discount;

import org.junit.Before;
import org.junit.Test;
import shop.discount.strategy.MultiBuyOffer;
import shop.discount.strategy.OneFreeProduct;
import shop.discount.strategy.PercentageOffSpendDiscount;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

public class DiscountFactoryTest {

    private DiscountFactory underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new DiscountFactory();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildDiscountShouldThrowExceptionIfUnknownString() throws Exception {
        Discount actual = underTest.buildDiscount("unknowon string");
    }

    @Test
    public void buildDiscountShouldReturnMultiBuy() throws Exception {
        Discount actual = underTest.buildDiscount("MULTIBUY_3_FOR_2");
        assertThat(actual.getPriority(), is(1));
        assertThat(actual.getStrategy(), is(instanceOf(MultiBuyOffer.class)));
    }

    @Test
    public void buildDiscountShouldReturnPercentageDiscount() throws Exception {
        Discount actual = underTest.buildDiscount("PERCENT_10_FOR_50_POUND_SPEND");
        assertThat(actual.getPriority(), is(100));
        assertThat(actual.getStrategy(), is(instanceOf(PercentageOffSpendDiscount.class)));
    }

    @Test
    public void buildDiscountShouldReturnOneFreeWidgetDiscount() throws Exception {
        Discount actual = underTest.buildDiscount("OneFreeWidget");
        assertThat(actual.getPriority(), is(3));
        assertThat(actual.getStrategy(), is(instanceOf(OneFreeProduct.class)));
    }
}
