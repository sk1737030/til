package com.clean.architecture.hexagonal.account.application.port.out;

import com.clean.architecture.hexagonal.account.domain.Account;
import java.time.LocalDateTime;

public class LoadAccountPort {

    public Account loadAccount(Account.AccountId accountId, LocalDateTime now) {
        return null;
    }
}
