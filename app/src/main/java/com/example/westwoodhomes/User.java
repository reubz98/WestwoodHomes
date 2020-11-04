package com.example.westwoodhomes;

public class User
{
    protected String Username;
    protected String Password;
    protected String Name;
    protected String Surname;
    protected boolean isAdmin;

    public String getUsername()
    {
        return Username;
    }

    public void setUsername(String username)
    {
        Username = username;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String password)
    {

        Password = password;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getSurname()
    {
        return Surname;
    }

    public void setSurname(String surname)
    {
        Surname = surname;
    }
    public User(String Username, String Password, String Name, String Surname)
    {
        this.Name = Name;
        this.Surname = Surname;
        this.Username = Username;
        this.Password = Password;
        isAdmin = false;
    }

    public User()
    {

    }

}




