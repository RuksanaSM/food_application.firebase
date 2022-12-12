package com.example.food_application.firebase.model;

public class UserClass {
    String Name;
    String Password;
    String Phone;

    public UserClass(String name, String password) {
        Name = name;
        Password = password;

    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public UserClass() {
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
