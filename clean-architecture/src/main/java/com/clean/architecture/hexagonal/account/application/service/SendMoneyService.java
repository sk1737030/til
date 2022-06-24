package com.clean.architecture.hexagonal.account.application.service;

import com.clean.architecture.hexagonal.account.application.port.in.SendMoneyCommand;
import com.clean.architecture.hexagonal.account.application.port.in.SendMoneyUseCase;
import com.clean.architecture.hexagonal.account.application.port.out.AccountLock;
import com.clean.architecture.hexagonal.account.application.port.out.LoadAccountPort;
import com.clean.architecture.hexagonal.account.application.port.out.UpdateAccountStatePort;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    public boolean sendMoney(SendMoneyCommand command) {
        // TODO: 비지니스 규칙 검증
        // TODO: 모델 상태 조작
        // TODO: 출력 값 반환
        return true;
    }

}
