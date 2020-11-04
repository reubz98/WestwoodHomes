package com.example.westwoodhomes;

import java.util.List;

public class Resident extends User
{
    private int UnitNo;
    private String[] Family;
    private List<Bill> Bills;

    public Resident()
    {

    }

    public Resident(String name, String surname, String username, String password, int UnitNo, String[] family){
        super(username, password, name, surname);
        this.UnitNo = UnitNo;
        this.Family = family;
    }

    public Resident(String name, String surname, String username, String password, int UnitNo){
        super(username, password, name, surname);
        this.UnitNo = UnitNo;
    }

    public int getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(int unitNo) {
        UnitNo = unitNo;
    }

    public String[] getFamily() {
        return Family;
    }

    public void setFamily(String[] family) {
        Family = family;
    }

    public List<Bill> getBills() {
        return Bills;
    }

    public void setBills(List<Bill> bills) {
        Bills = bills;
    }
}
