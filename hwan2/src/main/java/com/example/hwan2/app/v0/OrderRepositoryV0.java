package com.example.hwan2.app.v0;

public class OrderRepositoryV0 {

    public void save(String itemId) {
        if(itemId.equals("ex")) {
            throw new IllegalArgumentException("예외 발생!");
        }

        sleep(1000);
    }

    private void sleep( int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
