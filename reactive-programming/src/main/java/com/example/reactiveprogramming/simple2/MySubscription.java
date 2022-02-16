package com.example.reactiveprogramming.simple2;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.util.Iterator;

// 구독 정보(구독자, 어떤 데이터를 구독할건지)
public class MySubscription implements Subscription {

    private Subscriber s;
    private Iterator<Integer> it;

    public MySubscription(Subscriber s, Iterable<Integer> it) {
        this.s = s;
        this.it = it.iterator();
        System.out.println("신문사: 정보 등록 완료");
    }

    @Override
    public void request(long n) {
        while (n > 0) {
            if (it.hasNext()) {
                s.onNext(it.next()); // 1
            } else {
                s.onComplete();
                break;
            }
            n--;
        }
    }

    @Override
    public void cancel() {

    }
}
