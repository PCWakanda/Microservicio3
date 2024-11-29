package com.example.microservicio3.models;

public class SensorData {
    private int humidity;
    private int weather;
    private int temperature;
    private int pressure; // Nuevo campo
    private int flowRate; // Nuevo campo

    public SensorData(int humidity, int weather, int temperature, int pressure, int flowRate) {
        this.humidity = humidity;
        this.weather = weather;
        this.temperature = temperature;
        this.pressure = pressure;
        this.flowRate = flowRate;
    }

    // Getters y Setters
    public int getHumidity() {
        return humidity;
    }

    public int getWeather() {
        return weather;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public int getFlowRate() {
        return flowRate;
    }
}
