package com.example.repository;

import com.example.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*
FIXME:
 Look at com.example.model.Bank preamble.
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, UUID> {
}
