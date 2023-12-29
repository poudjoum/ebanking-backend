package com.jumpytech.ebankingbackend;

import com.jumpytech.ebankingbackend.dtos.BankAccountDTO;
import com.jumpytech.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.jumpytech.ebankingbackend.dtos.CustomerDTO;
import com.jumpytech.ebankingbackend.dtos.SavingBankAccountDTO;
import com.jumpytech.ebankingbackend.entities.*;
import com.jumpytech.ebankingbackend.enums.AccountStatus;
import com.jumpytech.ebankingbackend.enums.OperationType;
import com.jumpytech.ebankingbackend.exceptions.CustomerNotFoundException;
import com.jumpytech.ebankingbackend.repositories.AccountOperationRepository;
import com.jumpytech.ebankingbackend.repositories.BankAccountRepository;
import com.jumpytech.ebankingbackend.repositories.CustomerRepository;
import com.jumpytech.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);

    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
          Stream.of("Tchouamou","Gamom","Poudjoum Rodrigue").forEach(name->{
              CustomerDTO customer= new CustomerDTO();
              customer.setName(name);
              customer.setEmail(name+"@gmail.com");
              bankAccountService.saveCustomer(customer);
          });
          bankAccountService.listCustomers().forEach(cust->{
              try {
                  bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,cust.getId());
                  bankAccountService.saveSavingBankAccount(Math.random()*120000,2.8, cust.getId());



              } catch (CustomerNotFoundException e) {
                  e.printStackTrace();
              }
          });
            List<BankAccountDTO> bankAccounts=bankAccountService.bankAccountList();
            for(BankAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if (bankAccount instanceof SavingBankAccountDTO) {
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    } else {
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }

                    bankAccountService.credit(accountId, 10000 + Math.random() * 120000, "Credit ");
                    bankAccountService.debit(accountId, 1000 + Math.random() * 9000, "Debit");
                }
            }
        };

    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Poudjoum","Deumeni","Ngodjoum").forEach(name->{
                Customer customer= new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*100000);
                currentAccount.setDataCreation(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(10000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*100000);
                savingAccount.setDataCreation(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(acc->{
               for(int i=0;i<=9;i++){
                   AccountOperation accountOperation= new AccountOperation();
                   accountOperation.setOperationDate(new Date());
                   accountOperation.setAmount(Math.random()*12000);
                   accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                   accountOperation.setBankAccount(acc);
                   accountOperationRepository.save(accountOperation);
               }
            });
        };
    }

}
