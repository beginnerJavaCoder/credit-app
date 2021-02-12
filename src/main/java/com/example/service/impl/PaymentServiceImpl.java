package com.example.service.impl;

import com.example.model.Payment;
import com.example.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Payment calculatePayment(Double sum, Double debt, Double monthlyInterestRate) {
        Double sumOfRepaymentForCreditPercents = debt * monthlyInterestRate;

        Payment payment = new Payment();
        payment.setSum(sum);
        payment.setSumOfRepaymentForCreditPercents(sumOfRepaymentForCreditPercents);
        payment.setSumOfRepaymentForCreditBody(sum - sumOfRepaymentForCreditPercents);

        return payment;
    }
}
