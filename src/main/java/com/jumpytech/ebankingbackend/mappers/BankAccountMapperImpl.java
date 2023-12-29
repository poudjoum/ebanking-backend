package com.jumpytech.ebankingbackend.mappers;


import com.jumpytech.ebankingbackend.dtos.AccountOperationDTO;
import com.jumpytech.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.jumpytech.ebankingbackend.dtos.CustomerDTO;
import com.jumpytech.ebankingbackend.dtos.SavingBankAccountDTO;
import com.jumpytech.ebankingbackend.entities.AccountOperation;
import com.jumpytech.ebankingbackend.entities.CurrentAccount;
import com.jumpytech.ebankingbackend.entities.Customer;
import com.jumpytech.ebankingbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO= new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;

    }
    public Customer fromCustomer(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
        SavingBankAccountDTO dto=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,dto);
        dto.setCustomer(fromCustomer(savingAccount.getCustomer()));
        return dto;
    }
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO dto){
        SavingAccount savingAccount= new SavingAccount();
        BeanUtils.copyProperties(dto,savingAccount);
        savingAccount.setCustomer(fromCustomer(dto.getCustomer()));
        return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO dto=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,dto);
        dto.setCustomer(fromCustomer(currentAccount.getCustomer()));

        return dto;
    }
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO dto){
        CurrentAccount currentAccount= new CurrentAccount();
        BeanUtils.copyProperties(dto,currentAccount);
        currentAccount.setCustomer(fromCustomer(dto.getCustomer()));
        return currentAccount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO dto=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,dto);
         return dto;
    }
}
