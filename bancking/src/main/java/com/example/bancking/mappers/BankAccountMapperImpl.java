package com.example.bancking.mappers;

import com.example.bancking.dtos.AccountOperationDTO;
import com.example.bancking.dtos.CurrentBanckAccountDTO;
import com.example.bancking.dtos.CustomerDTO;
import com.example.bancking.dtos.SavingBanckAccountDTO;
import com.example.bancking.entities.AccountOperation;
import com.example.bancking.entities.CurrentAccount;
import com.example.bancking.entities.Customer;
import com.example.bancking.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
public SavingBanckAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
      SavingBanckAccountDTO savingBanckAccountDTO=new SavingBanckAccountDTO();
      BeanUtils.copyProperties(savingAccount,savingBanckAccountDTO);
      savingBanckAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
      savingBanckAccountDTO.setType(savingAccount.getClass().getSimpleName());
      return savingBanckAccountDTO;

}
    public SavingAccount fromSavingBankAccountDTO( SavingBanckAccountDTO  savingBanckAccountDTO){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBanckAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBanckAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    public CurrentBanckAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentBanckAccountDTO  currentBanckAccountDTO=new  CurrentBanckAccountDTO();
        BeanUtils.copyProperties( currentAccount, currentBanckAccountDTO);
        currentBanckAccountDTO.setType(currentAccount.getClass().getSimpleName());
        currentBanckAccountDTO.setCustomerDTO(fromCustomer( currentAccount.getCustomer()));
        return currentBanckAccountDTO;
    }
    public CurrentAccount fromCurrentBankAccountDTO( CurrentBanckAccountDTO  currentAccountDTO){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return  accountOperationDTO;
}

}
