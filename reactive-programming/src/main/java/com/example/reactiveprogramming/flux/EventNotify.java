package com.example.reactiveprogramming.flux;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventNotify {

    private final List<String> events = new ArrayList<>();
    private boolean change = false;

    public void add(String data) {
        events.add(data);
        change = true;
    }

    public boolean getChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public List<String> getEvents() {
        return events;
    }

}
