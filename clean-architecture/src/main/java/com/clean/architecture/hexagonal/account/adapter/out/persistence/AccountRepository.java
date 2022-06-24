package com.clean.architecture.hexagonal.account.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

interface AccountRepository extends JpaRepository<AccountJpaEntity, Long> {


}
