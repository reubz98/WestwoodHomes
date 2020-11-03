package com.example.westwoodhomes;

public class Unit {
    private int UnitNo;
    private int Bedrooms;
    private int Bathrooms;
    private int ParkingSpots;

    public Unit(){

    }

    public Unit(int unitNo, int bedrooms, int bathrooms, int parkingSpots){
        UnitNo = unitNo;
        Bedrooms = bedrooms;
        Bathrooms = bathrooms;
        ParkingSpots = parkingSpots;
    }

    public int getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(int unitNo) {
        UnitNo = unitNo;
    }

    public int getBedrooms() {
        return Bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        Bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return Bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        Bathrooms = bathrooms;
    }

    public int getParkingSpots() {
        return ParkingSpots;
    }

    public void setParkingSpots(int parkingSpots) {
        ParkingSpots = parkingSpots;
    }
}
