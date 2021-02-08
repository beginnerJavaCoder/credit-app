package com.example.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//TODO don't forget about bidirectional references while adding entries!!!
@Entity
@Table(name = "credit_offer")
public class CreditOffer extends Model {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    private Credit credit;
    @Column(name = "credit_amount")
    private Double creditAmount;
    @OneToMany(mappedBy = "creditOffer")
    private List<Payment> paymentSchedule;

    public CreditOffer() {
        paymentSchedule = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public List<Payment> getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(List<Payment> paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }
}
