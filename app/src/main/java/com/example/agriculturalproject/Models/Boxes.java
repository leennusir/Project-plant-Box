package com.example.agriculturalproject.Models;

public class Boxes {
    String img , name , plant , userId;

    public Boxes() {
    }

    public Boxes(String img, String name, String plant, String userId) {
        this.img = img;
        this.name = name;
        this.plant = plant;
        this.userId = userId;
    }

    public Boxes(String img, String name, String plant) {
        this.img = img;
        this.name = name;
        this.plant = plant;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
