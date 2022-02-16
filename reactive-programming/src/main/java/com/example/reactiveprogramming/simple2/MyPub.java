package com.example.reactiveprogramming.simple2;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import java.util.Arrays;

public class MyPub implements Publisher<Integer> {

    Iterable<Integer> it = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    @Override
    public void subscribe(Subscriber<? super Integer> s) {
        System.out.println("1. 신문사야 구독할께");
        System.out.println("2. 알겠어 --- 구독정보를 만들어서 돌려줄께 기다려");
        MySubscription mySubscription = new MySubscription(s, it);
        System.out.println("신문사야 구독 정보 생성 완료했어");
        s.onSubscribe(mySubscription);
    }
}
