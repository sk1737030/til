package com.example.simplekafka;

import com.example.simplekafka.producer.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class SimpleKafkaApplication implements CommandLineRunner {

    public static final String MATCHING_DATA = "mcs.local.matching.data";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SimpleKafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        kafkaTemplate.send(MATCHING_DATA, fixture());
    }

    private Order fixture() {
        return Order.builder()
            .orderId(8)
            .userId(1)
            .accountId(1234)
            .ndealQty(1)
            .businessDate("20210104")
            .symbol("BTCUSDT")
            .orderUuid(UUID.randomUUID().toString())
            .orderType("LIMIT")
            .orderSide("LONG")
            .orderPrice(BigDecimal.valueOf(10000))
            .orderMdCd("")
            .orderRoleCd("")
            .orderStatus("")
            .crctCnclCd("")
            .orderDlCd("")
            .orderQty(1L)
            .dealQty(1L)
            .crctCnclQty(1L)
            .displayQty(1L)
            .displayDealQty(1L)
            .displayCrctCnclQty(1L)
            .displayCnclQty(1L)
            .displayNdealQty(1L)
            .takerFeeRate(BigDecimal.valueOf(1))
            .makerFeeRate(BigDecimal.ONE)
            .hiddenFeeRate(BigDecimal.ONE)
            .appliedInitialMarginRate(BigDecimal.ONE)
            .appliedMaintenanceMarginRate(BigDecimal.ONE)
            .appliedPlaceFundingRate(BigDecimal.ONE)
            .postOnly(1)
            .timeInForce("")
            .orderStatusDetail("")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

}
