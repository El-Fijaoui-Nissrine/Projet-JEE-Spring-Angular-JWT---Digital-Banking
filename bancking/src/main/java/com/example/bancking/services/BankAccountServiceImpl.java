package com.example.bancking.services;
import com.example.bancking.entities.*;
import com.example.bancking.enums.OperationType;
import com.example.bancking.exceptions.BalanceNotSufficientException;
import com.example.bancking.exceptions.BankAccountNotFoundException;
import com.example.bancking.exceptions.CustomerNotFoundException;
import com.example.bancking.repositories.AccountOperationRepository;
import com.example.bancking.repositories.BankAccountRepository;
import com.example.bancking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("saving new customer");
        return  customerRepository.save( customer) ;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw  new CustomerNotFoundException("Customer not found");
        }
       CurrentAccount banckAccount=new CurrentAccount();
        banckAccount.setId(UUID.randomUUID().toString());
        banckAccount.setCreatedAt(new Date());
        banckAccount.setBalance(initialBalance);
        banckAccount.setCustomer(customer);
        banckAccount.setOverDraft(overDraft);
        return bankAccountRepository.save(banckAccount);
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw  new CustomerNotFoundException("Customer not found");
        }
        SavingAccount banckAccount =new SavingAccount();
        banckAccount.setId(UUID.randomUUID().toString());
        banckAccount.setCreatedAt(new Date());
        banckAccount.setBalance(initialBalance);
        banckAccount.setCustomer(customer);
        banckAccount.setInterstRate(interestRate);
        return bankAccountRepository.save(banckAccount);
    }


    @Override
    public List<Customer> listCustomer() {

        return customerRepository.findAll();
    }

    @Override
    public BanckAccount getBankAccuount(String accountId) throws BankAccountNotFoundException {
     BanckAccount banckAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("bankAccount not found"));
        return banckAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
BanckAccount banckAccount=getBankAccuount(accountId);
if(banckAccount.getBalance()<amount){throw new BalanceNotSufficientException("Balance not sufficient");}
        AccountOperation accountOperation=new AccountOperation();
accountOperation.setType(OperationType.DEBIT);
accountOperation.setOperationDate(new Date());
accountOperation.setAmount(amount);
accountOperation.setBanckAccount(banckAccount);
accountOperation.setDescription(description);
accountOperationRepository.save(accountOperation);
banckAccount.setBalance(banckAccount.getBalance()-amount);
bankAccountRepository.save(banckAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException{
        BanckAccount banckAccount=getBankAccuount(accountId);
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setAmount(amount);
        accountOperation.setBanckAccount(banckAccount);
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);
        banckAccount.setBalance(banckAccount.getBalance()+amount);
        bankAccountRepository.save(banckAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestinattion, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
debit(accountIdSource,amount,"transfer to "+accountIdDestinattion);
credit(accountIdDestinattion,amount,"transfer from "+accountIdSource);
    }
}
