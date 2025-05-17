package com.example.bancking.web;

import com.example.bancking.dtos.BanckAccountDTO;
import com.example.bancking.exceptions.BankAccountNotFoundException;
import com.example.bancking.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;
@GetMapping("/accounts/{accountId}")
  public BanckAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
    BanckAccountDTO banckAccountDTO=bankAccountService.getBankAccuount(accountId);
return banckAccountDTO;
  }
@GetMapping("/accounts")
    public List<BanckAccountDTO> listAccounts(){
    return bankAccountService.listBankAccount();
}


}
