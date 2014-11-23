package shop.discount;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DiscountResultMatcher extends TypeSafeMatcher<DiscountResult>{
    private DiscountResult actual;

    public DiscountResultMatcher(DiscountResult actual) {
        this.actual = actual;
    }

    @Override
    protected boolean matchesSafely(DiscountResult item) {
        return actual.getAmount().compareTo(item.getAmount()) == 0;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("should match amount: ").appendText(String.valueOf(actual.getAmount()));
    }
}
