package com.clean.architecture.hexagonal.account.application.service;

import com.clean.architecture.hexagonal.account.domain.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MoneyTransferProperties {

    private Money maximumTransferThreshold = Money.of(1_000_000L);

}