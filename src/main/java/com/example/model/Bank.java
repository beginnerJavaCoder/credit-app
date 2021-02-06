package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Table;

//TODO think about reorganization of this class (it has no logic currently)
@Entity
@Table(name = "client_credit")
public class Bank extends Model {

    //TODO write references for that
    private Client client;
    private Credit credit;

    public Bank() {

    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }
}
