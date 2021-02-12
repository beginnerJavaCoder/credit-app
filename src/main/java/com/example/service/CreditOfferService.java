package com.example.service;

import com.example.model.CreditOffer;

public interface CreditOfferService {

    Double getMonthlyPaymentAmount(CreditOffer creditOffer);
    Double getTotalAmountOfCredit(CreditOffer creditOffer);
    Double getTotalAmountOfInterestRate(CreditOffer creditOffer);
    void save(CreditOffer creditOffer);
}
