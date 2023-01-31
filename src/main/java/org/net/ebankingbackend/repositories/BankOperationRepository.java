package org.net.ebankingbackend.repositories;

import org.net.ebankingbackend.entites.BankAccout;
import org.net.ebankingbackend.entites.BankOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BankOperationRepository extends JpaRepository<BankOperation,Long> {

    public List<BankOperation> findByBankAccout(BankAccout accountId);
    public Page<BankOperation> findByBankAccout(BankAccout accountId, Pageable pageable);
}

