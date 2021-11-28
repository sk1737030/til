package com.example.hwan1.hello.core.discount;


import com.example.hwan1.hello.core.member.Member;

public interface DisCountPolicy {

    int discount(Member member, int itemPrice);
}
