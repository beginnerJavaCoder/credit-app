package com.example.service;

import com.example.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer findById(UUID id);
    List<Customer> findAll();
    List<Customer> findAll(String surname);
    void delete(Customer customer);
    void save(Customer customer);
}
