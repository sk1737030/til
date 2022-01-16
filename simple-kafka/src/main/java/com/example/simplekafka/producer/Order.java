package com.example.simplekafka.producer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private long orderId;
    private long accountId;
    private long ndealQty;
    private String businessDate;
    private long userId;
    private String symbol;
    private String orderUuid;
    private String orderType;
    private String orderSide;
    private String orderMdCd;
    private String orderRoleCd;
    private String orderStatus;
    private String crctCnclCd;
    private String orderDlCd;
    private BigDecimal orderPrice;
    private Long orderQty;
    private Long dealQty;
    private Long crctCnclQty;
    private Long cnclQty;
    private Long displayQty;
    private Long displayDealQty;
    private Long displayCrctCnclQty;
    private Long displayCnclQty;
    private Long displayNdealQty;
    private BigDecimal takerFeeRate;
    private BigDecimal makerFeeRate;
    private BigDecimal hiddenFeeRate;
    private BigDecimal appliedInitialMarginRate;
    private BigDecimal appliedMaintenanceMarginRate;
    private BigDecimal appliedPlaceFundingRate;
    private Integer postOnly;
    private String timeInForce;
    private String orderStatusDetail;
    private String triggerType;
    private BigDecimal triggerPrice;
    private BigDecimal trailValue;
    private BigDecimal filledAvgPrice;
    private String orderAlias;
    private String createdIp;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;
    private BigDecimal appliedMaintenenceMarginRate;

}
