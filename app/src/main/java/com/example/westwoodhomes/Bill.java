package com.example.westwoodhomes;

import java.util.Date;

public class Bill {
    private String Type;
    private double Amount;
    private String DateIssued;
    private boolean Paid;

    public Bill(){

    }

    public Bill(String type, double amount, String dateIssued){
        Type = type;
        Amount = amount;
        DateIssued = dateIssued;
        Paid = false;
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

    public String getDateIssued() {
        return DateIssued;
    }

    public void setDateIssued(String dateIssued) {
        DateIssued = dateIssued;
    }

    public boolean isPaid() {
        return Paid;
    }

    public void setPaid(boolean paid) {
        Paid = paid;
    }
}
