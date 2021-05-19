package com.example.agriculturalproject.Models;

public class Boxes {
    String img, name, plant, userId, Led ,Temperature ,Humidity; // String name from FB (حرفيا)


    public Boxes() {
    }

    public Boxes(String img, String name, String plant, String userId, String led, String temperature, String humidity) {
        this.img = img;
        this.name = name;
        this.plant = plant;
        this.userId = userId;
        Led = led;
        Temperature = temperature;
        Humidity = humidity;
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

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLed() {
        return Led;
    }

    public void setLed(String led) {
        Led = led;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }
}


