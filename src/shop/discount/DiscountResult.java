package shop.discount;

import java.math.BigDecimal;

public class DiscountResult {
    private BigDecimal amount;


    public DiscountResult(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
