package com.example.bancking.services;

import com.example.bancking.dtos.*;
import com.example.bancking.entities.BanckAccount;
import com.example.bancking.entities.CurrentAccount;
import com.example.bancking.entities.Customer;
import com.example.bancking.entities.SavingAccount;
import com.example.bancking.exceptions.BalanceNotSufficientException;
import com.example.bancking.exceptions.BankAccountNotFoundException;
import com.example.bancking.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
  CustomerDTO saveCustomer(CustomerDTO customerDTO);
   CurrentBanckAccountDTO saveCurrentBankAccount (double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
 SavingBanckAccountDTO saveSavingBankAccount (double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
   List<CustomerDTO> listCustomer();
   BanckAccountDTO getBankAccuount (String accountId) throws BankAccountNotFoundException;
   void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount,String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestinattion,double amount ) throws BankAccountNotFoundException, BalanceNotSufficientException;
 List<BanckAccountDTO> listBankAccount();


 CustomerDTO getCustomer(Long id) throws CustomerNotFoundException;

 CustomerDTO updateCustomer(CustomerDTO customerDTO);

 void deletCustomer(Long customerId);

 List<AccountOperationDTO> accountHistory(String accountId);

 AccountHistoryDTO  getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
