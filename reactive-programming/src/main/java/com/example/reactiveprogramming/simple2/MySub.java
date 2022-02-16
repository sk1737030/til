package com.example.reactiveprogramming.simple2;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySub implements Subscriber<Integer> {

    private Subscription s;
    private int bufferSize = 2;

    @Override
    public void onSubscribe(Subscription s) {
        System.out.println("구독자: 구독 정보 잘받았어");
        this.s = s;
        System.out.println("구독자: 나 신문 한개씩 매일 매일 줘");
        s.request(bufferSize); // 신문 한개씩 매일 매일 줘 // (백프러셔) 소비자가 한번에 처리할 수 있는 개수를 요청
    }

    @Override
    public void onNext(Integer integer) {
        System.out.println("onNext() : " + integer);
        System.out.println("하루 지남 ");
        bufferSize--;
        if (bufferSize == 0) {
            bufferSize = 2;
            s.request(bufferSize);
        }
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("subscribe 에러");
    }

    @Override
    public void onComplete() {
        System.out.println("subscribe 완료");
    }
}
