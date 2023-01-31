package org.net.ebankingbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.net.ebankingbackend.entites.BankAccout;
import org.net.ebankingbackend.enums.OperationType;

import javax.persistence.*;
import java.util.Date;


@Data
public class BankOperationDto {

    private  Long id;
    private  Date operationdate;
    private  double amount;
    private  OperationType type;
    private  String description;

}
