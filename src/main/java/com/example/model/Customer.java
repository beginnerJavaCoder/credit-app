package com.example.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends Model {

    private String surname;
    @Column(name = "first_name")
    private String firstName;
    private String patronymic;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String passport;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Bank> entries;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CreditOffer> creditOffers;

    public Customer() {
        entries = new ArrayList<>();
        creditOffers = new ArrayList<>();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
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
