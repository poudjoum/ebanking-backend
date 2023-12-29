package com.jumpytech.ebankingbackend.dtos;

import com.jumpytech.ebankingbackend.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
public class SavingBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date dataCreation;
    private String currency;
    private AccountStatus status;
    private CustomerDTO customer;
    private double interestRate;
}
