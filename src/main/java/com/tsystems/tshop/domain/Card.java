package com.tsystems.tshop.domain;

public class Card {

    private Integer idcards;
    private String cardsnumber;
    private Integer cvv;
    private Integer balance;
    private Integer ordersum;
    private String confirmcode;
    private String cardholder;
    private String phone;

    public Card(){}
    public Card(Integer idcards, String cardsnumber, Integer cvv, Integer balance, String cardholder, String phone) {
        this.idcards = idcards;
        this.cardsnumber = cardsnumber;
        this.cvv = cvv;
        this.balance = balance;
        this.cardholder = cardholder;
        this.phone = phone;
    }

    public Integer getIdcards() {
        return idcards;
    }

    public void setIdcards(Integer idcards) {
        this.idcards = idcards;
    }

    public String getCardsnumber() {
        return cardsnumber;
    }

    public void setCardsnumber(String cardsnumber) {
        this.cardsnumber = cardsnumber;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOrdersum() {
        return ordersum;
    }

    public void setOrdersum(Integer ordersum) {
        this.ordersum = ordersum;
    }

    public String getConfirmcode() {
        return confirmcode;
    }

    public void setConfirmcode(String confirmcode) {
        this.confirmcode = confirmcode;
    }
}
