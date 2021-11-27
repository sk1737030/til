package com.example.hwan1.hello.core.order;

public class Order {

    private final Long memberId;
    private final String itemName;
    private final int itemPrice;
    private final int discountPrice;

    public Order(Long memberId, String itemName, int itemPrice, int discountPrice) {
        this.memberId = memberId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
    }
}
