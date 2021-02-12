package com.example.service.impl;

import com.example.model.CreditOffer;
import com.example.model.Payment;
import com.example.repository.CreditOfferRepository;
import com.example.service.CreditOfferService;
import com.example.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditOfferServiceImpl implements CreditOfferService {

    private final CreditOfferRepository creditOfferRepository;
    private final PaymentService paymentService;

    @Autowired
    public CreditOfferServiceImpl(CreditOfferRepository creditOfferRepository, PaymentService paymentService) {
        this.creditOfferRepository = creditOfferRepository;
        this.paymentService = paymentService;
    }

    public Double getMonthlyPaymentAmount(CreditOffer creditOffer) {
        Double interestRate = creditOffer.getCredit().getInterestRate();
        Double creditAmount = creditOffer.getCreditAmount();
        Double monthlyInterestRate = getMonthlyInterestRate(interestRate);
        //TODO change amount of months
        Double tmp = Math.pow(1 + monthlyInterestRate, 36);
        Double monthlyPaymentAmount = creditAmount * ((monthlyInterestRate * tmp) / (tmp - 1));

        return monthlyPaymentAmount;
    }

    private Double getMonthlyInterestRate(Double interestRate) {
        return (interestRate / 100.0) / 12.0;
    }

    public Double getTotalAmountOfCredit(CreditOffer creditOffer) {
        //TODO type months
        return 36 * getMonthlyPaymentAmount(creditOffer);
    }

    public Double getTotalAmountOfInterestRate(CreditOffer creditOffer) {
        return getTotalAmountOfCredit(creditOffer) - creditOffer.getCreditAmount();
    }

    @Override
    public List<Payment> calculatePaymentSchedule(CreditOffer creditOffer) {
        List<Payment> paymentSchedule = new ArrayList<>();
        Double monthlyPaymentAmount = getMonthlyPaymentAmount(creditOffer);
        Double totalCreditAmount = creditOffer.getCreditAmount();
        Double monthlyInterestRate = getMonthlyInterestRate(creditOffer.getCredit().getInterestRate());
        //TODO change 36 months
        Payment tmp;
        for (int i = 0; i < 36; i++) {
            tmp = paymentService.calculatePayment(monthlyPaymentAmount, totalCreditAmount, monthlyInterestRate);
            paymentSchedule.add(tmp);
            totalCreditAmount -= tmp.getSumOfRepaymentForCreditBody();
        }

        return paymentSchedule;
    }

    @Override
    public void save(CreditOffer creditOffer) {
        creditOfferRepository.save(creditOffer);
    }
}
