package org.net.ebankingbackend.mappers;


import org.net.ebankingbackend.dtos.*;
import org.net.ebankingbackend.entites.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMapperImpl {


    public CustomerDto fromCustomer(Customer customer) {
        CustomerDto customerDto=new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }
    public Customer fromCustomerDto(CustomerDto customerDto){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;

    }

    public SavingBankAccoutDto fromSavingAccount(SavingAccount savingAccount){
        SavingBankAccoutDto savingBankAccoutDto=new SavingBankAccoutDto();
        BeanUtils.copyProperties(savingAccount,savingBankAccoutDto);
        savingBankAccoutDto.setCustomer(fromCustomer(savingAccount.getCustomer()));
        savingBankAccoutDto.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccoutDto;

    }
    public SavingAccount fromSavingAccountDto(SavingBankAccoutDto savingBankAccoutDto){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccoutDto,savingAccount);
        savingAccount.setCustomer(fromCustomerDto(savingBankAccoutDto.getCustomer()));
        return savingAccount;

    }
    public CurrentBankAccoutDto fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccoutDto currentBankAccoutDto=new CurrentBankAccoutDto();
        BeanUtils.copyProperties(currentAccount,currentBankAccoutDto);
        currentBankAccoutDto.setCustomer(fromCustomer(currentAccount.getCustomer()));
        currentBankAccoutDto.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccoutDto;

    }
    public CurrentAccount fromCurrentAccountDto(CurrentBankAccoutDto currentBankAccoutDto){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccoutDto,currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentBankAccoutDto.getCustomer()));
        return currentAccount;

    }
    public BankOperationDto fromBankAccountOperation(BankOperation bankOperation){
        BankOperationDto bankOperationDto=new BankOperationDto();
        BeanUtils.copyProperties(bankOperation,bankOperationDto);
        return bankOperationDto;
    }

}


