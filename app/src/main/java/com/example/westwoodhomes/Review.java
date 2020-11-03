package com.example.westwoodhomes;

public class Review {
    private String ReviewBody;
    private String Name;
    private int Rating;
    private int UnitNo;

    public Review(){

    }

    public Review(String reviewBody, String name, int rating, int unitNo){
        ReviewBody = reviewBody;
        Name = name;
        Rating = rating;
        UnitNo = unitNo;
    }

    public String getReviewBody() {
        return ReviewBody;
    }

    public void setReviewBody(String reviewBody) {
        ReviewBody = reviewBody;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public int getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(int unitNo) {
        UnitNo = unitNo;
    }
}
