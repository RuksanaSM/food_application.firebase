package com.example.food_app2.model;

import java.util.List;

public class FoodModel {
    private String Description,Image,MenuId,Name,Price;
  //  private long price;
    private List<AddOnModel> addon;
    private List<SizeModel> sizeModelList;

    public FoodModel() {
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public List<AddOnModel> getAddon() {
        return addon;
    }

    public void setAddon(List<AddOnModel> addon) {
        this.addon = addon;
    }

    public List<SizeModel> getSizeModelList() {
        return sizeModelList;
    }

    public void setSizeModelList(List<SizeModel> sizeModelList) {
        this.sizeModelList = sizeModelList;
    }
}
