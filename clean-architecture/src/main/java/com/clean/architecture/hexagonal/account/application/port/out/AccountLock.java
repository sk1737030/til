package com.clean.architecture.hexagonal.account.application.port.out;

import com.clean.architecture.hexagonal.account.domain.Account;

public interface AccountLock {

    void lockAccount(Account.AccountId accountId);

    void releaseAccount(Account.AccountId accountId);

}