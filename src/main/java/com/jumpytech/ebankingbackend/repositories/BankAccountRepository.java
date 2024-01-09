package com.jumpytech.ebankingbackend.repositories;

import com.jumpytech.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

  List<BankAccount> findBankAccountByCustomer_Id(Long custId);


}
