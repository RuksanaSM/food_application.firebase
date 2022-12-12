package com.example.food_application.firebase.model;

public class Category_ModelClass {

    private String Name;
    private String Image;


    public Category_ModelClass() {
    }


    public Category_ModelClass(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}

