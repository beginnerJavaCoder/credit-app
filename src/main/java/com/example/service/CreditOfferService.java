package com.example.service;

import com.example.model.CreditOffer;
import com.example.model.Payment;

import java.util.List;

public interface CreditOfferService {

    Double getMonthlyPaymentAmount(CreditOffer creditOffer, Double months);
    Double getTotalAmountOfCredit(CreditOffer creditOffer, Double months);
    Double getTotalAmountOfInterestRate(CreditOffer creditOffer, Double months);
    List<Payment> calculatePaymentSchedule(CreditOffer creditOffer, Double months);
    void save(CreditOffer creditOffer);
}
