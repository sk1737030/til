package com.clean.architecture.hexagonal.account.application.service;

import com.clean.architecture.hexagonal.account.application.port.in.GetAccountBalanceQuery;
import com.clean.architecture.hexagonal.account.application.port.out.LoadAccountPort;
import com.clean.architecture.hexagonal.account.domain.Account;
import com.clean.architecture.hexagonal.account.domain.Money;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {

    public final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(Account.AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now()).calculateBalance();
    }

}
