package com.jumpytech.ebankingbackend.repositories;

import com.jumpytech.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
