package com.example.hwan2.log.hellotrace;

import com.example.hwan2.log.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV2Test {

    @Test
    void begin_end() {
        HelloTraceV2 traceV2 = new HelloTraceV2();
        TraceStatus status1 = traceV2.begin("hello1");
        TraceStatus status2 = traceV2.beginSync(status1.getTraceId(), "hello1");
        traceV2.end(status1);
        traceV2.end(status2);
    }


    @Test
    void begin_exception() {
        HelloTraceV2 traceV2 = new HelloTraceV2();
        TraceStatus status1 = traceV2.begin("hello1");
        TraceStatus status2 = traceV2.beginSync(status1.getTraceId(), "hello2");
        traceV2.exception(status1, new IllegalArgumentException());
        traceV2.exception(status2, new IllegalArgumentException());
    }

}