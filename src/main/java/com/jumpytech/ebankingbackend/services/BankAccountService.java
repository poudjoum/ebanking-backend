package com.jumpytech.ebankingbackend.services;

import com.jumpytech.ebankingbackend.dtos.CustomerDTO;
import com.jumpytech.ebankingbackend.entities.BankAccount;
import com.jumpytech.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.jumpytech.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.jumpytech.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    BankAccount saveCurrentBankAccount(double initialBalance, double overDraft,Long customerId) throws CustomerNotFoundException, CustomerNotFoundException;
    BankAccount saveSavingBankAccount(double initialBalance, double interestRate,Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();

    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfert(String accountIdSource,String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccount>bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long custId);
}
