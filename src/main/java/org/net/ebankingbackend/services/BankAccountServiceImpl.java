package org.net.ebankingbackend.services;

import lombok.extern.slf4j.Slf4j;
import org.net.ebankingbackend.dtos.*;
import org.net.ebankingbackend.entites.*;
import org.net.ebankingbackend.enums.OperationType;
import org.net.ebankingbackend.exeptions.BalanceNotSufficentException;
import org.net.ebankingbackend.exeptions.BanAccountNotFoundExecption;
import org.net.ebankingbackend.exeptions.CustomerNotFoundExeception;
import org.net.ebankingbackend.mappers.CustomerMapperImpl;
import org.net.ebankingbackend.repositories.BankAccountRepository;
import org.net.ebankingbackend.repositories.BankOperationRepository;
import org.net.ebankingbackend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankOperationRepository bankOperationRepository;
    @Autowired
    private CustomerMapperImpl customerMapper;



    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer=customerMapper.fromCustomerDto(customerDto);
        Customer saveCustomer=customerRepository.save(customer);
        return customerMapper.fromCustomer(saveCustomer);
    }

    @Override
    public CurrentBankAccoutDto saveCurrentBankAccount(Double initialBalance, Long idCustomer, double overDraft) throws CustomerNotFoundExeception {
        Customer customer = customerRepository.findById(idCustomer).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundExeception("customer not found ");
        }
        CurrentAccount currentBankAccount = new CurrentAccount();
        currentBankAccount.setId(UUID.randomUUID().toString());
        currentBankAccount.setCreatedAt(new Date());
        currentBankAccount.setBalance(initialBalance);
        currentBankAccount.setCustomer(customer);
        currentBankAccount.setOverDraft(overDraft);

        return customerMapper.fromCurrentAccount(bankAccountRepository.save(currentBankAccount));


    }

    @Override
    public SavingBankAccoutDto saveSavingBankAccount(Double initialBalance, Long idCustomer, double interestRate) throws CustomerNotFoundExeception {
        Customer customer = customerRepository.findById(idCustomer).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundExeception("customer not found ");
        }
        SavingAccount savingBankAccount = new SavingAccount();
        savingBankAccount.setId(UUID.randomUUID().toString());
        savingBankAccount.setCreatedAt(new Date());
        savingBankAccount.setBalance(initialBalance);
        savingBankAccount.setCustomer(customer);
        savingBankAccount.setInterestRate(interestRate);

        return customerMapper.fromSavingAccount(bankAccountRepository.save(savingBankAccount));


    }


    @Override
    public List<CustomerDto> listCustomers() {
        List<Customer> customers = customerRepository.findAll();

        List<CustomerDto> customerDtos = customers.stream()
                .map(customer -> customerMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        return customerDtos;
    }

    @Override
    public BankAccountDto getBankAcounts(String accountId) throws BanAccountNotFoundExecption {
        BankAccout bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BanAccountNotFoundExecption("BankAccount not found"));
        if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return customerMapper.fromSavingAccount(savingAccount);

        } else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return customerMapper.fromCurrentAccount(currentAccount);


        }
    }



    @Override
    public void debit(String accountId, double amount, String description) throws BanAccountNotFoundExecption, BalanceNotSufficentException {
        BankAccout bankAccount= bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BanAccountNotFoundExecption("BankAccount not found"));
        if (bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficentException("Banlace not sufficient ");
        }
        BankOperation bankOperation = new BankOperation();
        bankOperation.setType(OperationType.Debit);
        bankOperation.setAmount(amount);
        bankOperation.setDescription(description);
        bankOperation.setOperationdate(new Date());
        bankOperation.setBankAccout(bankAccount);
        bankOperationRepository.save(bankOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BanAccountNotFoundExecption {
        BankAccout bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BanAccountNotFoundExecption("BankAccount not found"));
        BankOperation bankOperation = new BankOperation();
        bankOperation.setType(OperationType.CREDIT);
        bankOperation.setAmount(amount);
        bankOperation.setDescription(description);
        bankOperation.setOperationdate(new Date());
        bankOperation.setBankAccout(bankAccount);
        bankOperationRepository.save(bankOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void trasfer(String accountIdSource, String acountIdDestination, double amount) throws BanAccountNotFoundExecption, BalanceNotSufficentException {

        debit(accountIdSource, amount, "transfer to " + acountIdDestination);
        credit(acountIdDestination, amount, "trasfer from " + accountIdSource);


    }

    @Override
    public List<BankAccountDto> bankaccontlist() {
      List<BankAccout> bankAccounts= bankAccountRepository.findAll();
     List<BankAccountDto> bankAccountDtos= bankAccounts.stream().map(bankAccount->{
          if (bankAccount instanceof SavingAccount ){
              SavingAccount savingAccount=(SavingAccount) bankAccount;
              return customerMapper.fromSavingAccount(savingAccount);

          }else {
              CurrentAccount currentAccount=(CurrentAccount) bankAccount;
              return customerMapper.fromCurrentAccount(currentAccount);

          }

      }).collect(Collectors.toList());
     return bankAccountDtos;

    }

    @Override
    public CustomerDto getCustomers(Long idCustomers) throws CustomerNotFoundExeception {
        Customer customer = customerRepository.findById(idCustomers)
                .orElseThrow(() -> new CustomerNotFoundExeception("Cusotomer Not found"));
        return customerMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Customer customer=customerMapper.fromCustomerDto(customerDto);
        Customer saveCustomer=customerRepository.save(customer);
        return customerMapper.fromCustomer(saveCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId){
       customerRepository.deleteById(customerId);
    }
    @Override
    public List<BankOperationDto> historyAccount(BankAccout accountId){
        List<BankOperation> bankOperations=bankOperationRepository.findByBankAccout(accountId);

        return bankOperations.stream().map(op->customerMapper.fromBankAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public HistoryAccountDto getAccountHystory(BankAccout accountId, int page, int size) throws BanAccountNotFoundExecption {
        BankAccout bankAccout=bankAccountRepository.findById(accountId.getId()).orElse(null);
        if (bankAccout==null) throw new BanAccountNotFoundExecption("Account not faound");
      Page<BankOperation> bankOperations = bankOperationRepository.findByBankAccout(accountId, PageRequest.of(page,size));
      HistoryAccountDto historyAccountDto=new HistoryAccountDto();
      List<BankOperationDto> bankOperationDtos=bankOperations.getContent().stream().map(op->customerMapper.fromBankAccountOperation(op)).collect(Collectors.toList());
      historyAccountDto.setBankOperationDtos(bankOperationDtos);
      historyAccountDto.setAccountId(bankAccout.getId());
      historyAccountDto.setBalance(bankAccout.getBalance());
      historyAccountDto.setCurrentPage(page);
      historyAccountDto.setPageSize(size);
      historyAccountDto.setTotalPage(bankOperations.getTotalPages());
        return historyAccountDto;
    }


}

