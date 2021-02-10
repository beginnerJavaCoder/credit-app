package com.example.repository;

import com.example.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Integer> {

    List<Credit> findByInterestRate(Double interestRate);
}
