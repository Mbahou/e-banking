package org.net.ebankingbackend.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.net.ebankingbackend.enums.OperationType;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankOperation  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  Date operationdate;
    private  double amount;
    @Enumerated(EnumType.STRING)
    private  OperationType type;
    private String description;
    @ManyToOne
    private BankAccout bankAccout;
}
