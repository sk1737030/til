package com.example.hwan1.hello.core.service;

import com.example.hwan1.hello.core.domain.Order;

public interface OrderService {

    Order createOrder(Long memberId, String itemName, int itemPrice);
}
