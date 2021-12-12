package com.example.hwan2.log.logtrace;

import com.example.hwan2.log.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus traeStatus, Exception e);

}
