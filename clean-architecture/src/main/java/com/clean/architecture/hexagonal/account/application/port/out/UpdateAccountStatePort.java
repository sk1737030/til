package com.clean.architecture.hexagonal.account.application.port.out;


import com.clean.architecture.hexagonal.account.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}