package com.example.hwan1.autowired;

import com.example.hwan1.hello.core.config.AutoAppConfig;
import com.example.hwan1.hello.core.discount.DisCountPolicy;
import com.example.hwan1.hello.core.member.Member;
import com.example.hwan1.hello.core.grade.Grade;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;
import java.util.Map;

class AllBeanTest {

    @Test
    void findAllBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member userA = new Member(1L, "userA", Grade.VIP);
        discountService.discount(userA, 10000, "fixDiscountPolicy");
    }

    static class DiscountService {

        private final Map<String, DisCountPolicy> policyMap;
        private final List<DisCountPolicy> policies;

        public DiscountService(Map<String, DisCountPolicy> policyMap, List<DisCountPolicy> plicies) {
            this.policyMap = policyMap;
            this.policies = plicies;

            System.out.println("policyMap = " + policyMap);
            System.out.println("plicies = " + plicies);
        }

        public int discount(Member member, int price, String discountCode) {
            DisCountPolicy disCountPolicy = policyMap.get(discountCode);
            return disCountPolicy.discount(member, price);
        }
    }

}
