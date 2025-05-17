package com.example.bancking.web;

import com.example.bancking.dtos.CustomerDTO;
import com.example.bancking.entities.Customer;
import com.example.bancking.exceptions.CustomerNotFoundException;
import com.example.bancking.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomer();
    }
@GetMapping("/customers/{id}")
    public CustomerDTO getCustomer( @PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
return  bankAccountService.getCustomer(customerId);
}

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO )  {
        return  bankAccountService.saveCustomer(customerDTO);
    }
@PutMapping ("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
}
@DeleteMapping ("/customers/{customerId}")
    public void deletCustomer(@PathVariable Long customerId){
        bankAccountService.deletCustomer(customerId);
}
}
