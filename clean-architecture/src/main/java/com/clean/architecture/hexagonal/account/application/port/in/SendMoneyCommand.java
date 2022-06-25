package com.clean.architecture.hexagonal.account.application.port.in;

import com.clean.architecture.hexagonal.account.common.SelfValidating;
import com.clean.architecture.hexagonal.account.domain.Account;
import com.clean.architecture.hexagonal.account.domain.Money;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final Account.AccountId sourceAccountId;
    @NotNull
    private final Account.AccountId targetAccountId;
    @NotNull
    private Money money;

    public SendMoneyCommand(Account.AccountId sourceAccountId, Account.AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        requireGreaterThan(money);
        this.validateSelf();
    }

    private void requireGreaterThan(Money money) {
        if (!money.isPositive()) {
            throw new IllegalArgumentException();
        }
    }

}
