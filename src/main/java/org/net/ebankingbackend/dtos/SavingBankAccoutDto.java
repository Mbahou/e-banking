package org.net.ebankingbackend.dtos;


import lombok.Data;
import org.net.ebankingbackend.entites.BankOperation;
import org.net.ebankingbackend.enums.AccountStatus;
import java.util.Date;
import java.util.List;


@Data

public  class SavingBankAccoutDto extends BankAccountDto {

    private String id;
    private Double balance;
    private Date createdAt;
    private AccountStatus accountStatus;
    private CustomerDto customer;
    private  double interestRate;


}
