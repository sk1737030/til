package com.example.hwan1.hello.core.service;

import com.example.hwan1.hello.core.discount.DisCountPolicy;
import com.example.hwan1.hello.core.repository.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    public OrderServiceImpl(MemoryMemberRepository memberRepository, DisCountPolicy discountPolicy) {

    }
}
