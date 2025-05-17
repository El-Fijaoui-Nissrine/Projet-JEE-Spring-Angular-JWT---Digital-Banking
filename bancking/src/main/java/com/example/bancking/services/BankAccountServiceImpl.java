package com.example.bancking.services;
import com.example.bancking.dtos.BanckAccountDTO;
import com.example.bancking.dtos.CurrentBanckAccountDTO;
import com.example.bancking.dtos.CustomerDTO;
import com.example.bancking.dtos.SavingBanckAccountDTO;
import com.example.bancking.entities.*;
import com.example.bancking.enums.OperationType;
import com.example.bancking.exceptions.BalanceNotSufficientException;
import com.example.bancking.exceptions.BankAccountNotFoundException;
import com.example.bancking.exceptions.CustomerNotFoundException;
import com.example.bancking.mappers.BankAccountMapperImpl;
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
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl bankAccountMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("saving new customer");
        Customer customer=bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer saveCustomer=customerRepository.save(customer);
        return  bankAccountMapper.fromCustomer(saveCustomer) ;
    }

    @Override
    public CurrentBanckAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
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
        return bankAccountMapper.fromCurrentBankAccount(bankAccountRepository.save(banckAccount)) ;
    }

    @Override
    public SavingBanckAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
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
        return   bankAccountMapper.fromSavingBankAccount(bankAccountRepository.save(banckAccount));
    }


    @Override
    public List<CustomerDTO> listCustomer() {
        List<Customer> customerList= customerRepository.findAll();
        List<CustomerDTO> customerDTOList=customerList.stream()
                .map(customer -> bankAccountMapper.fromCustomer(customer))
                .collect(Collectors.toList());
       return customerDTOList;
    }

    @Override
    public BanckAccountDTO getBankAccuount(String accountId) throws BankAccountNotFoundException {
     BanckAccount banckAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("bankAccount not found"));
     if (banckAccount instanceof SavingAccount){
         SavingAccount savingAccount=(SavingAccount) banckAccount;
         return bankAccountMapper.fromSavingBankAccount(savingAccount);
     }else{
         CurrentAccount currentAccount=(CurrentAccount) banckAccount;
         return bankAccountMapper.fromCurrentBankAccount(currentAccount);}

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BanckAccount banckAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("bankAccount not found"));

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
        BanckAccount banckAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("bankAccount not found"));
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

    @Override
    public List<BanckAccountDTO> listBankAccount() {
        List<BanckAccount> banckAccounts=bankAccountRepository.findAll();
       List<BanckAccountDTO> banckAccountDTOS= banckAccounts.stream().map(banckAccount -> {
            if (banckAccount instanceof SavingAccount){
                SavingAccount savingAccount=(SavingAccount) banckAccount;
                return bankAccountMapper.fromSavingBankAccount(savingAccount);
            }else{
                CurrentAccount currentAccount=(CurrentAccount) banckAccount;
                return bankAccountMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return banckAccountDTOS;
    }
    @Override
    public CustomerDTO getCustomer(Long id) throws CustomerNotFoundException {
Customer customer =customerRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
return bankAccountMapper.fromCustomer(customer);

    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("update  customer");
        Customer customer=bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer saveCustomer=customerRepository.save(customer);
        return  bankAccountMapper.fromCustomer(saveCustomer) ;
    }
@Override
public void deletCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }
}
