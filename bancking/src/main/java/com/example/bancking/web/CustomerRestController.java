package com.example.bancking.web;

import com.example.bancking.dtos.CustomerDTO;
import com.example.bancking.entities.Customer;
import com.example.bancking.exceptions.CustomerNotFoundException;
import com.example.bancking.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin( "*")

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('USER')")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomer();
    }
    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('USER')")

    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "")String keyword){
        return bankAccountService.searchCustomers("%"+keyword+"%");
    }
@GetMapping("/customers/{id}")
@PreAuthorize("hasAuthority('USER')")

    public CustomerDTO getCustomer( @PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
return  bankAccountService.getCustomer(customerId);
}

    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('ADMIN')")

    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO )  {
        return  bankAccountService.saveCustomer(customerDTO);
    }
@PutMapping ("/customers/{customerId}")
@PreAuthorize("hasAuthority('ADMIN')")

    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
}
@DeleteMapping ("/customers/{customerId}")
@PreAuthorize("hasAuthority('ADMIN')")

    public void deletCustomer(@PathVariable Long customerId){
        bankAccountService.deletCustomer(customerId);
}
}
