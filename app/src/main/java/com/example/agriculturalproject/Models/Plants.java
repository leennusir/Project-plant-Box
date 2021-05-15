package com.example.agriculturalproject.Models;

public class Plants {
    String description , img , name, temperature;

    public Plants() {
    }

    public Plants(String description, String img, String name, String temperature) {
        this.description = description;
        this.img = img;
        this.name = name;
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
