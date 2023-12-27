package com.jumpytech.ebankingbackend.services;

import com.jumpytech.ebankingbackend.dtos.CustomerDTO;
import com.jumpytech.ebankingbackend.entities.*;
import com.jumpytech.ebankingbackend.enums.OpertationType;
import com.jumpytech.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.jumpytech.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.jumpytech.ebankingbackend.exceptions.CustomerNotFoundException;
import com.jumpytech.ebankingbackend.mappers.BankAccountMapperImpl;
import com.jumpytech.ebankingbackend.repositories.AccountOperationRepository;
import com.jumpytech.ebankingbackend.repositories.BankAccountRepository;
import com.jumpytech.ebankingbackend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor

public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;
   // Logger log= LoggerFactory.getLogger(this.getClass().getName());


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Client");

        Customer customer=dtoMapper.fromCustomer(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public BankAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {

        Customer cust=customerRepository.findById(customerId).orElse(null);
        if(cust==null)
            throw new CustomerNotFoundException("Customer not found");
            CurrentAccount currentAccount= new CurrentAccount();
            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setDataCreation(new Date());
            currentAccount.setBalance(initialBalance);
            currentAccount.setOverDraft(overDraft);
            currentAccount.setCustomer(cust);
            CurrentAccount savedBankAccount=bankAccountRepository.save(currentAccount);
            return savedBankAccount;

    }

    @Override
    public BankAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer cust=customerRepository.findById(customerId).orElse(null);
        if(cust==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingAccount savingAccount= new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setDataCreation(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(cust);
        SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);
        return savedBankAccount;
    }




    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> cust=customerRepository.findAll();
        List<CustomerDTO> customerDTOS=cust.stream().map(customer ->
                dtoMapper.fromCustomer(customer)).collect(Collectors.toList());

        /*List<CustomerDTO> customerDTOS= new ArrayList<>();
        for(Customer customer:cust){
            CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }*/

        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).
                orElseThrow(()->new BankAccountNotFoundException("BankAccount Not found"));

        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount= getBankAccount(accountId);
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OpertationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescriptiion(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {

        BankAccount bankAccount= getBankAccount(accountId);
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OpertationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescriptiion(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {

        debit(accountIdDestination,amount,"Transfert to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfert from:"+accountIdSource);


    }

    @Override
    public List<BankAccount> bankAccountList() {
        return bankAccountRepository.findAll();
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer cust=customerRepository.findById(customerId).orElseThrow(
                ()->new CustomerNotFoundException("customer not found")
        );
        return dtoMapper.fromCustomer(cust);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        log.info("Updating customer.......!");
        Customer customer=dtoMapper.fromCustomer(customerDTO);
        Customer updatesCustomer=customerRepository.save(customer);
        return dtoMapper.fromCustomer(updatesCustomer);

    }
    @Override
    public void deleteCustomer(Long custId){
        customerRepository.deleteById(custId);
    }
}
