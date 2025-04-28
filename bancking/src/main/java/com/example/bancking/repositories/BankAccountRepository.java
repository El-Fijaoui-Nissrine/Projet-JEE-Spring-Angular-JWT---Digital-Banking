package com.example.bancking.repositories;

import com.example.bancking.entities.BanckAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BanckAccount,String> {
}
