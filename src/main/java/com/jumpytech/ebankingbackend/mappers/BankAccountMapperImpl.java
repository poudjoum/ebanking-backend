package com.jumpytech.ebankingbackend.mappers;


import com.jumpytech.ebankingbackend.dtos.CustomerDTO;
import com.jumpytech.ebankingbackend.entities.Customer;
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
}
