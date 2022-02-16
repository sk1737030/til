package com.example.reactiveprogramming.simple2;

public class App {

    public static void main(String[] args) {
        MyPub pub = new MyPub(); // 신문사 생성
        MySub sub = new MySub(); // 구독

        pub.subscribe(sub);
    }

}
