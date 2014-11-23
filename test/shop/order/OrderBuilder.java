package shop.order;

import shop.discount.DiscountResult;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OrderBuilder {
    private List<OrderLine> orderLines = newArrayList();
    private List<DiscountResult> discountResults = newArrayList();

    public static OrderBuilder orderBuilder() {
        return new OrderBuilder();
    }

    public Order build() {
        return new Order(orderLines, discountResults);
    }

    public OrderBuilder orderLines(List<OrderLine> orderLines){
        this.orderLines = orderLines;
        return this;
    }

    public OrderBuilder discountResults(List<DiscountResult> discountResults){
        this.discountResults = discountResults;
        return this;
    }

    public OrderBuilder orderLine(OrderLine orderLine){
        this.orderLines.add(orderLine);
        return this;
    }

    public OrderBuilder discountResult(DiscountResult discountResult){
        this.discountResults.add(discountResult);
        return this;
    }

}
