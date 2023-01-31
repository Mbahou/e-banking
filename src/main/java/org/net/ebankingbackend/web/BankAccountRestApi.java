package org.net.ebankingbackend.web;

import lombok.AllArgsConstructor;
import org.net.ebankingbackend.dtos.BankAccountDto;
import org.net.ebankingbackend.dtos.BankOperationDto;
import org.net.ebankingbackend.dtos.HistoryAccountDto;
import org.net.ebankingbackend.entites.BankAccout;
import org.net.ebankingbackend.exeptions.BanAccountNotFoundExecption;
import org.net.ebankingbackend.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestApi {
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/account/{accountId}")
    public BankAccountDto getBankAccount(@PathVariable String accountId) throws BanAccountNotFoundExecption {
        return bankAccountService.getBankAcounts(accountId);

    }
    @GetMapping("/accounts")
    public List<BankAccountDto> listAccount(){
        return bankAccountService.bankaccontlist();

    }
    @GetMapping("/accounts/{accountId}/operation")
    public List<BankOperationDto> getHistory(@PathVariable BankAccout accountId){
        return bankAccountService.historyAccount(accountId);
    }
    @GetMapping("/accounts/{accountId}/pageOperation")
    public HistoryAccountDto getAccountHistory(@PathVariable BankAccout accountId,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "5") int size) throws BanAccountNotFoundExecption {
        return bankAccountService.getAccountHystory(accountId,page,size);
    }







}
