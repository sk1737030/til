package com.example.hwan1.hello.core.discount;

import com.example.hwan1.hello.core.member.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class RateDiscountPolicy implements DisCountPolicy {

    @Override
    public int discount(Member member, int itemPrice) {
        return 0;
    }
}
