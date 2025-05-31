package com.example.bancking;

import com.example.bancking.dtos.BanckAccountDTO;
import com.example.bancking.dtos.CurrentBanckAccountDTO;
import com.example.bancking.dtos.CustomerDTO;
import com.example.bancking.dtos.SavingBanckAccountDTO;
import com.example.bancking.entities.*;
import com.example.bancking.enums.AccountStatus;
import com.example.bancking.enums.OperationType;
import com.example.bancking.exceptions.BalanceNotSufficientException;
import com.example.bancking.exceptions.BankAccountNotFoundException;
import com.example.bancking.exceptions.CustomerNotFoundException;
import com.example.bancking.repositories.AccountOperationRepository;
import com.example.bancking.repositories.BankAccountRepository;
import com.example.bancking.repositories.CustomerRepository;
import com.example.bancking.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BanckingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanckingApplication.class, args);
	}
@Bean
		CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return args -> {
			Stream.of("Amine","Ali","Nissrine","Zineb").forEach(name->{
				CustomerDTO customer=new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
bankAccountService.listCustomer().forEach(customer -> {
    try {
        bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
		bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());


    } catch (CustomerNotFoundException e) {
        e.printStackTrace();
    }
});
			List<BanckAccountDTO> banckAccountList= bankAccountService.listBankAccount();
			for (BanckAccountDTO banckAccount:banckAccountList){
				for (int i=0;i<10;i++){
					String accountId;
					if (banckAccount instanceof SavingBanckAccountDTO){
						accountId=((SavingBanckAccountDTO) banckAccount).getId();
					}else {
						accountId=((CurrentBanckAccountDTO) banckAccount).getId();

					}
					bankAccountService.credit(accountId,1000+Math.random()*1200001,"credit");
					bankAccountService.debit(accountId,1000+Math.random()*90000,"debit");
				}
			}

		};
}
	//@Bean
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
