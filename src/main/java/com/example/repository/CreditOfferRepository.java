package com.example.repository;

import com.example.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditOfferRepository extends JpaRepository<Credit, Integer> {

}
