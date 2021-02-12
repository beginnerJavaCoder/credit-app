package com.example.service;

import com.example.model.Credit;

import java.util.List;
import java.util.UUID;

public interface CreditService {

    Credit findById(UUID id);
    List<Credit> findAll();
    List<Credit> findAll(Double interestRate);
    void delete(Credit credit);
    void save(Credit credit);
}
