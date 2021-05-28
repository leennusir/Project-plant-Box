package com.example.agriculturalproject.Models;

public class Plants {
    String description , img , name, temperature,humidity,water_level,climate;

    public Plants() {
    }

    public Plants(String description, String img, String name, String temperature, String humidity, String water_level, String climate) {
        this.description = description;
        this.img = img;
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
        this.water_level = water_level;
        this.climate = climate;
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

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWater_level() {
        return water_level;
    }

    public void setWater_level(String water_level) {
        this.water_level = water_level;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }
}
