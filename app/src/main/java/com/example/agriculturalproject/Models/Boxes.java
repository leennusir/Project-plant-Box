package com.example.agriculturalproject.Models;

public class Boxes {
    String img, name, plant, userId, Led, Temperature, Humidity, Soil_Moisture, Water_Level, pump, AC,Fan ,Led_auto; // String name from FB (حرفيا)

    public Boxes() {
    }

    public Boxes(String img, String name, String plant, String userId, String led, String temperature,
                 String humidity, String soil_Moisture, String water_Level, String pump, String AC, String fan, String led_auto) {
        this.img = img;
        this.name = name;
        this.plant = plant;
        this.userId = userId;
        Led = led;
        Temperature = temperature;
        Humidity = humidity;
        Soil_Moisture = soil_Moisture;
        Water_Level = water_Level;
        this.pump = pump;
        this.AC = AC;
        Fan = fan;
        Led_auto = led_auto;
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

    public String getSoil_Moisture() {
        return Soil_Moisture;
    }

    public void setSoil_Moisture(String soil_Moisture) {
        Soil_Moisture = soil_Moisture;
    }

    public String getWater_Level() {
        return Water_Level;
    }

    public void setWater_Level(String water_Level) {
        Water_Level = water_Level;
    }

    public String getPump() {
        return pump;
    }

    public void setPump(String pump) {
        this.pump = pump;
    }

    public String getAC() {
        return AC;
    }

    public void setAC(String AC) {
        this.AC = AC;
    }

    public String getFan() {
        return Fan;
    }

    public void setFan(String fan) {
        Fan = fan;
    }

    public String getLed_auto() {
        return Led_auto;
    }

    public void setLed_auto(String led_auto) {
        Led_auto = led_auto;
    }
}