package com.jumpytech.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("SAV")

public class CurrentAccount  extends BankAccount {
    private double overDraft;
}
