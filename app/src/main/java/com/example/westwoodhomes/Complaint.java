package com.example.westwoodhomes;

public class Complaint
{
    private String Type;
    private String Description;

    public Complaint()
    {

    }

    public Complaint(String type, String description)
    {
        Type = type;
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
