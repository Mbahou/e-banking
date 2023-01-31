package org.net.ebankingbackend.services;

import org.net.ebankingbackend.dtos.*;
import org.net.ebankingbackend.entites.BankAccout;
import org.net.ebankingbackend.entites.CurrentAccount;
import org.net.ebankingbackend.entites.Customer;
import org.net.ebankingbackend.entites.SavingAccount;
import org.net.ebankingbackend.exeptions.BalanceNotSufficentException;
import org.net.ebankingbackend.exeptions.BanAccountNotFoundExecption;
import org.net.ebankingbackend.exeptions.CustomerNotFoundExeception;

import java.util.List;

public interface BankAccountService {

    //Customer  saveCustomerDto(CustomerDto  customerDto);

    CustomerDto saveCustomer(CustomerDto customerDto);

    CurrentBankAccoutDto saveCurrentBankAccount(Double initialBalance, Long idCustomer, double overDraft) throws CustomerNotFoundExeception;

    SavingBankAccoutDto saveSavingBankAccount(Double initialBalance, Long idCustomer, double interestRate) throws CustomerNotFoundExeception;


    List<CustomerDto> listCustomers();

    BankAccountDto getBankAcounts(String accountId) throws BanAccountNotFoundExecption;

    void debit(String accoutId, double amount, String description) throws BanAccountNotFoundExecption, BalanceNotSufficentException;

    void credit(String accoutId, double amount, String description) throws BanAccountNotFoundExecption, BalanceNotSufficentException;

    void trasfer(String AccountIdSource, String AccountIdDestination, double amount) throws BanAccountNotFoundExecption, BalanceNotSufficentException;

    List<BankAccountDto> bankaccontlist();

    CustomerDto getCustomers(Long idCustomers) throws CustomerNotFoundExeception;


    CustomerDto updateCustomer(CustomerDto customerDto);


    void deleteCustomer(Long customerId);


    List<BankOperationDto> historyAccount(BankAccout accountId);

    HistoryAccountDto getAccountHystory(BankAccout accountId, int page, int size) throws BanAccountNotFoundExecption;


}