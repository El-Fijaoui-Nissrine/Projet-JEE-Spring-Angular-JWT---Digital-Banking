package com.example.bancking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@DiscriminatorValue("SA")

@Entity
public class SavingAccount extends BanckAccount{
    private double interstRate;
}
