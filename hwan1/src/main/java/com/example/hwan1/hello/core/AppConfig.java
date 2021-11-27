package com.example.hwan1.hello.core;

import com.example.hwan1.hello.core.discount.DisCountPolicy;
import com.example.hwan1.hello.core.discount.RateDiscountPolicy;
import com.example.hwan1.hello.core.repository.MemoryMemberRepository;
import com.example.hwan1.hello.core.service.MemberService;
import com.example.hwan1.hello.core.service.MemberServiceImpl;
import com.example.hwan1.hello.core.service.OrderService;
import com.example.hwan1.hello.core.service.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //@Bean memberService -> new MemoryMemberRespotiory()
    //@Bean orderService -> new MemoryMemberRespotiory();

    @Bean
    public MemberService memberService() {
        System.out.println("call Appconfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call Appconfig.memberReposiory ");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call Appconfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DisCountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }


}
