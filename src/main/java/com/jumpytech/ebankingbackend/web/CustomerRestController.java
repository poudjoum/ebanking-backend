package com.jumpytech.ebankingbackend.web;

import com.jumpytech.ebankingbackend.dtos.CustomerDTO;
import com.jumpytech.ebankingbackend.entities.Customer;
import com.jumpytech.ebankingbackend.exceptions.CustomerNotFoundException;
import com.jumpytech.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customerList(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name="id") long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);

    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

        return bankAccountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable Long id){

        bankAccountService.deleteCustomer(id) ;
    }

}
