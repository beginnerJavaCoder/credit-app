package com.example.service;

import com.example.model.Payment;

public interface PaymentService {
    Payment calculatePayment(Double sum, Double debt, Double monthlyInterestRate);
}
