package com.jumpytech.ebankingbackend.repositories;

import com.jumpytech.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
