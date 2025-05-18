package com.example.bancking.web;

import com.example.bancking.dtos.AccountHistoryDTO;
import com.example.bancking.dtos.AccountOperationDTO;
import com.example.bancking.dtos.BanckAccountDTO;
import com.example.bancking.exceptions.BankAccountNotFoundException;
import com.example.bancking.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin( "*")

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
@GetMapping("/accounts/{accountId}/operations")
public  List<AccountOperationDTO> getHistory(@PathVariable String accountId){
    return bankAccountService.accountHistory(accountId);
}
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId ,
                                               @RequestParam(name = "page",defaultValue="0") int page,
                                               @RequestParam(name = "size",defaultValue="0") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
}
