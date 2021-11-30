package com.example.hwan1.autowired;

import com.example.hwan1.hello.core.AutoAppConfig;
import com.example.hwan1.hello.core.discount.DisCountPolicy;
import com.example.hwan1.hello.core.grade.Grade;
import com.example.hwan1.hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AllBeanTest {

    @Test
    void findAllBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member userA = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(userA, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);
    }

    static class DiscountService {

        private final Map<String, DisCountPolicy> policyMap;
        private final List<DisCountPolicy> policies;

        public DiscountService(Map<String, DisCountPolicy> policyMap, List<DisCountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DisCountPolicy disCountPolicy = policyMap.get(discountCode);
            return disCountPolicy.discount(member, price);
        }
    }

}
