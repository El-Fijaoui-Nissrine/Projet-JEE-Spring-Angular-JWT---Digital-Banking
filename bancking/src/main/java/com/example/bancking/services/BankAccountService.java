package com.example.bancking.services;

import com.example.bancking.entities.BanckAccount;
import com.example.bancking.entities.CurrentAccount;
import com.example.bancking.entities.Customer;
import com.example.bancking.entities.SavingAccount;
import com.example.bancking.exceptions.BalanceNotSufficientException;
import com.example.bancking.exceptions.BankAccountNotFoundException;
import com.example.bancking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
  Customer saveCustomer(Customer customer);
   CurrentAccount saveCurrentBankAccount (double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount (double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
   List<Customer> listCustomer();
   BanckAccount getBankAccuount (String accountId) throws BankAccountNotFoundException;
   void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount,String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestinattion,double amount ) throws BankAccountNotFoundException, BalanceNotSufficientException;



}
