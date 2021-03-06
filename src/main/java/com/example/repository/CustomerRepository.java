package com.example.repository;

import com.example.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findBySurnameStartingWithIgnoreCase(String surname);
}
