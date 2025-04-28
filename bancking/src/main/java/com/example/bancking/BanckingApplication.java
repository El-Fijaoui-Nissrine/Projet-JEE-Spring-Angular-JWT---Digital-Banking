package com.example.bancking;

import com.example.bancking.entities.AccountOperation;
import com.example.bancking.entities.CurrentAccount;
import com.example.bancking.entities.Customer;
import com.example.bancking.entities.SavingAccount;
import com.example.bancking.enums.AccountStatus;
import com.example.bancking.enums.OperationType;
import com.example.bancking.repositories.AccountOperationRepository;
import com.example.bancking.repositories.BankAccountRepository;
import com.example.bancking.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BanckingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanckingApplication.class, args);
	}
@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository){
		return args->{
			Stream.of("hassan","Yassine","Yassmine","Aicha").forEach(name->{
				Customer customer=new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);
			});
			customerRepository.findAll().forEach(cust->{
				CurrentAccount currentAccount=new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(90000);
				bankAccountRepository.save(currentAccount);
				SavingAccount savingAccount=new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterstRate(5.5);
				bankAccountRepository.save(savingAccount);
			});

bankAccountRepository.findAll().forEach(acc->{
	for (int i =0;i<10;i++){
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setOperationDate(new Date());
		accountOperation.setAmount(Math.random()*12000);
		accountOperation.setType(Math.random()<0.5? OperationType.DEBIT: OperationType.CREDIT);
		accountOperation.setBanckAccount(acc);
		accountOperationRepository.save(accountOperation);
	}

});
		};
	}







}
