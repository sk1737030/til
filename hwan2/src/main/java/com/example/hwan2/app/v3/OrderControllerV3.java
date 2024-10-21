package com.example.hwan2.app.v3;

import com.example.hwan2.log.logtrace.LogTrace;
import com.example.hwan2.log.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderServiceV0;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderServiceV0.orderItem(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

        return "ok";
    }

    @GetMapping("/ups/v3/union-products/flat")
    public String requestTest(@RequestParam("gids") List<Long> gids) throws InterruptedException {
        TraceStatus status = null;
        log.info("LocalDateTIme STart now : {}" , LocalDateTime.now());

        Thread.sleep(6000);
        System.out.println(gids);
        try {
//            status = trace.begin("OrderController.request()");
//            orderServiceV0.orderItem(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
        log.info("LocalDateTIme End now : {}" , LocalDateTime.now());

        return "ok";
    }
}
