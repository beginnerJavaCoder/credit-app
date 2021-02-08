package com.example.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "payment")
public class Payment extends Model {

    private Timestamp date;
    private Double sum;
    @Column(name = "body_rep_sum")
    private Double sumOfRepaymentForCreditBody;
    @Column(name = "percents_rep_sum")
    private Double sumOfRepaymentForCreditPercents;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_offer_id")
    private CreditOffer creditOffer;

    public Payment() {

    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getSumOfRepaymentForCreditBody() {
        return sumOfRepaymentForCreditBody;
    }

    public void setSumOfRepaymentForCreditBody(Double sumOfRepaymentForCreditBody) {
        this.sumOfRepaymentForCreditBody = sumOfRepaymentForCreditBody;
    }

    public Double getSumOfRepaymentForCreditPercents() {
        return sumOfRepaymentForCreditPercents;
    }

    public void setSumOfRepaymentForCreditPercents(Double sumOfRepaymentForCreditPercents) {
        this.sumOfRepaymentForCreditPercents = sumOfRepaymentForCreditPercents;
    }

    public CreditOffer getCreditOffer() {
        return creditOffer;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }
}
