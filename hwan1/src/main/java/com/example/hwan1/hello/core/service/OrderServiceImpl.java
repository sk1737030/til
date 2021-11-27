package com.example.hwan1.hello.core.service;

import com.example.hwan1.hello.core.discount.DisCountPolicy;
import com.example.hwan1.hello.core.domain.Order;
import com.example.hwan1.hello.core.repository.MemberRepository;
import java.lang.reflect.Member;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DisCountPolicy disCountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DisCountPolicy disCountPolicy) {
        this.memberRepository = memberRepository;
        this.disCountPolicy = disCountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = disCountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
