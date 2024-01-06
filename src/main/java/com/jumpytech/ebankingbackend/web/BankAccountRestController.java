package com.jumpytech.ebankingbackend.web;

import com.jumpytech.ebankingbackend.dtos.*;
import com.jumpytech.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.jumpytech.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.jumpytech.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")

public class BankAccountRestController {
    private final BankAccountService bankAccountService;

    public BankAccountRestController (BankAccountService bankAccountService){
        this.bankAccountService=bankAccountService;
    }
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO>listAccounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.AccountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getHistory(@PathVariable String accountId,
                                              @RequestParam(name="page",defaultValue = "0") int page,
                                              @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO dto) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(dto.getAccountId(),dto.getAmount(),dto.getDescription());
        return dto;

    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO dto) throws BankAccountNotFoundException {
        this.bankAccountService.credit(dto.getAccountId(),dto.getAmount(),dto.getDescription());
        return dto;
    }
    @PostMapping("/accounts/transfert")
    public void transfert(@RequestBody TransfertRequestDTO dto) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfert(dto.getAccountSource(),dto.getAccountDestination(),dto.getAmount());

    }
}
