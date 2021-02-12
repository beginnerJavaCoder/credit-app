package com.example.service;

import com.example.model.CreditOffer;
import com.example.model.Payment;

import java.util.List;

public interface CreditOfferService {

    Double getMonthlyPaymentAmount(CreditOffer creditOffer);
    Double getTotalAmountOfCredit(CreditOffer creditOffer);
    Double getTotalAmountOfInterestRate(CreditOffer creditOffer);
    List<Payment> calculatePaymentSchedule(CreditOffer creditOffer);
    void save(CreditOffer creditOffer);
}
