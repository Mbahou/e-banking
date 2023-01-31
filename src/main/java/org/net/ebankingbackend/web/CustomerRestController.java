package org.net.ebankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.net.ebankingbackend.dtos.CustomerDto;
import org.net.ebankingbackend.exeptions.CustomerNotFoundExeception;
import org.net.ebankingbackend.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {

    @Autowired
    private BankAccountService bankAccountService;

   @GetMapping("/customers")
    public List<CustomerDto> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDto getCustomers(@PathVariable(name = "id") Long idCustomers) throws CustomerNotFoundExeception {
       return  bankAccountService.getCustomers(idCustomers);
    }

    @PostMapping("/customers")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
       return bankAccountService.saveCustomer (customerDto);

    }
    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomer(@PathVariable(name = "id") Long customerId,@RequestBody CustomerDto customerDto){
       customerDto.setId(customerId);
       return bankAccountService.updateCustomer(customerDto);

    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer( @PathVariable(name = "id ") Long customerId){

       bankAccountService.deleteCustomer(customerId);
    }
}
