package com.example.westwoodhomes;

import java.util.Date;

public class Bill {
    private String Type;
    private double Amount;
    private Date DateIssued;

    public Bill(){

    }

    public Bill(String type, double amount, Date dateIssued){
        Type = type;
        Amount = amount;
        DateIssued = dateIssued;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public Date getDateIssued() {
        return DateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        DateIssued = dateIssued;
    }
}
