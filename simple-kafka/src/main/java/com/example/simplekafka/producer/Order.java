package com.example.simplekafka.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private long orderId;
    private String price;
    private String orderType;

}
