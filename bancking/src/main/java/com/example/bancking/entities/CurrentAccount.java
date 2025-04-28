package com.example.bancking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//@DiscriminatorValue("CA")

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentAccount extends BanckAccount{
    private double overDraft;
}
