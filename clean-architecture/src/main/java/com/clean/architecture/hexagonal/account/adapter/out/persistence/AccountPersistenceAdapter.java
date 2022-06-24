package com.clean.architecture.hexagonal.account.adapter.out.persistence;

import com.clean.architecture.hexagonal.account.application.port.out.LoadAccountPort;
import com.clean.architecture.hexagonal.account.application.port.out.UpdateAccountStatePort;
import com.clean.architecture.hexagonal.account.domain.Account;
import com.clean.architecture.hexagonal.account.domain.Activity;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate) {
        AccountJpaEntity account =
            accountRepository.findById(accountId.getValue()).orElseThrow(EntityNotFoundException::new);

        List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(
            accountId.getValue(),
            baselineDate
        );

        Long withdrawalBalance = orZero(activityRepository.getWithdrawalBalanceUntil(accountId.getValue(),
            baselineDate
        ));

        Long depositBalance = orZero(activityRepository.getDepositBalanceUntil(
            accountId.getValue(),
            baselineDate
        ));

        return accountMapper.mapToDomainEntity(
            account,
            activities,
            withdrawalBalance,
            depositBalance
        );
    }

    @Override
    public void updateActivities(Account account) {
        for (Activity activity : account.getActivityWindow().getActivities()) {
            if (account.getId().isEmpty()) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }

}
