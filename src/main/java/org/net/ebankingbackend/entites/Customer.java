package org.net.ebankingbackend.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.net.ebankingbackend.dtos.CustomerDto;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer{
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id ;
        private String name;
        private String email;
        @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private List<BankAccout> bankAccouts;
}
