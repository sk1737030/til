package com.clean.architecture.hexagonal.account.application.port.in;

import com.clean.architecture.hexagonal.account.domain.Account;
import com.clean.architecture.hexagonal.account.domain.Money;

public interface GetAccountBalanceQuery {

    Money getAccountBalance(Account.AccountId accountId);
}
