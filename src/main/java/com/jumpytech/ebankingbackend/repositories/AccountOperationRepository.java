package com.jumpytech.ebankingbackend.repositories;

import com.jumpytech.ebankingbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
