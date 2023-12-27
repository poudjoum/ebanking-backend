package com.jumpytech.ebankingbackend.entities;

import com.jumpytech.ebankingbackend.enums.OpertationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OpertationType type;
    @ManyToOne
    private BankAccount bankAccount;
    private String descriptiion;
}
