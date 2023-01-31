package org.net.ebankingbackend.dtos;


import lombok.Data;
import org.net.ebankingbackend.enums.AccountStatus;

import java.util.Date;


@Data

public  class CurrentBankAccoutDto extends BankAccountDto{

    private String id;
    private Double balance;
    private Date createdAt;
    private AccountStatus accountStatus;
    private CustomerDto customer;
    private  double overDraft;


}
