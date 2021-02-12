package com.example.model;

import javax.persistence.*;

/*
FIXME:
 There is a fictive entity for requirements of the technical task.
 Lists of all customers or credits are possible to get through
 methods findAll() in necessary repository interfaces / services.
 Also, mapping of customers and their credits are stored in creditOffer table.
 So, this table is unnecessary (for now).
 */
@Entity
@Table(name = "customer_credit")
public class Bank extends Model {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    public Bank() {

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
}
