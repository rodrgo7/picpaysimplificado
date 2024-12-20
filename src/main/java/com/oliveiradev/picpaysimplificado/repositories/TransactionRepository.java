package com.oliveiradev.picpaysimplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oliveiradev.picpaysimplificado.domain.transactions.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
