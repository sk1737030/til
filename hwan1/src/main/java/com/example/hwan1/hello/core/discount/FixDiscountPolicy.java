package com.example.hwan1.hello.core.discount;

import com.example.hwan1.hello.core.grade.Grade;
import com.example.hwan1.hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicy implements DisCountPolicy {

    public static final int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int itemPrice) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
