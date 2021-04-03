package com.example.agriculturalproject.Models;

public class Users {
    String FirstName , LastName , Email ,Password ,Select ;

    public Users() {
    }

    public Users(String firstName, String lastName, String email, String password ,String select) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;
        Select = select;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSelect() {
        return Select;
    }

    public void setSelect(String select) {
        Select = select;
    }
}
