package com.example.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//TODO maybe I should add limit for number of months (time limit for pay all the credit)
@Entity
@Table(name = "credit")
public class Credit extends Model {

    @Column(name = "lim")
    private Double limit;
    @Column(name = "interest_rate")
    private Double interestRate;
    @OneToMany(mappedBy = "credit", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Bank> entries;
    @OneToMany(mappedBy = "credit", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CreditOffer> creditOffers;

    public Credit() {
        entries = new ArrayList<>();
        creditOffers = new ArrayList<>();
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public List<Bank> getEntries() {
        return entries;
    }

    public void setEntries(List<Bank> entries) {
        this.entries = entries;
    }

    public List<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffers(List<CreditOffer> creditOffers) {
        this.creditOffers = creditOffers;
    }
}
