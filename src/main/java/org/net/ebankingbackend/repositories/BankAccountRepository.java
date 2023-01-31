package org.net.ebankingbackend.repositories;

import org.net.ebankingbackend.entites.BankAccout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccout,String> {
}
