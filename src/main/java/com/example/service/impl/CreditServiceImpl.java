package com.example.service.impl;

import com.example.model.Credit;
import com.example.repository.CreditRepository;
import com.example.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Override
    public Credit findById(UUID id) {
        return creditRepository.findById(id).orElse(null);
    }

    @Override
    public List<Credit> findAll() {
        return creditRepository.findAll();
    }

    @Override
    public List<Credit> findAll(Double interestRate) {
        return creditRepository.findByInterestRate(interestRate);
    }

    @Override
    public void delete(Credit credit) {
        creditRepository.delete(credit);
    }

    @Override
    public void save(Credit credit) {
        creditRepository.save(credit);
    }
}
