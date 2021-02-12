package com.example.service.impl;

import com.example.model.CreditOffer;
import com.example.repository.CreditOfferRepository;
import com.example.service.CreditOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditOfferServiceImpl implements CreditOfferService {

    private final CreditOfferRepository creditOfferRepository;

    @Autowired
    public CreditOfferServiceImpl(CreditOfferRepository creditOfferRepository) {
        this.creditOfferRepository = creditOfferRepository;
    }

    public Double getMonthlyPaymentAmount(CreditOffer creditOffer) {
        Double interestRate = creditOffer.getCredit().getInterestRate();
        Double creditAmount = creditOffer.getCreditAmount();
        Double monthlyInterestRate = (interestRate / 100.0) / 12.0;
        //TODO change amount of months
        Double tmp = Math.pow(1 + monthlyInterestRate, 36);
        Double monthlyPaymentAmount = creditAmount * ((monthlyInterestRate * tmp) / (tmp - 1));

        return monthlyPaymentAmount;
    }

    public Double getTotalAmountOfCredit(CreditOffer creditOffer) {
        //TODO type months
        return 36 * getMonthlyPaymentAmount(creditOffer);
    }

    public Double getTotalAmountOfInterestRate(CreditOffer creditOffer) {
        return getTotalAmountOfCredit(creditOffer) - creditOffer.getCreditAmount();
    }

    @Override
    public void save(CreditOffer creditOffer) {
        creditOfferRepository.save(creditOffer);
    }
}
