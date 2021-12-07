package com.example.hwan2.app.vo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepositoryVO orderRepository;

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }

}
