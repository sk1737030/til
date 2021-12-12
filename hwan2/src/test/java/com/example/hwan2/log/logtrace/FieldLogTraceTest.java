package com.example.hwan2.log.logtrace;

import com.example.hwan2.log.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class FieldLogTraceTest {

    FieldLogTrace trace = new FieldLogTrace();

    @Test
    void begin_end_level2() {
        TraceStatus begin1 = trace.begin("hello1");
        TraceStatus begin2 = trace.begin("hello2");

        trace.end(begin1);
        trace.end(begin2);
    }

    @Test
    void begin_exception_level2() {
        TraceStatus begin1 = trace.begin("hello1");
        TraceStatus begin2 = trace.begin("hello2");

        trace.exception(begin1, new IllegalArgumentException());
        trace.exception(begin2, new IllegalArgumentException());
    }
}

