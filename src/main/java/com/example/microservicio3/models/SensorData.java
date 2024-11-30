package com.example.microservicio3.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "sensor_data")
public class SensorData {
    private static int sensorDataCounter = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int humidity;
    private int weather;
    private int temperature;
    private int pressure;
    private int flowRate;
    private int tick;

    public SensorData() {
    }

    public SensorData(int humidity, int weather, int temperature, int pressure, int flowRate) {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.humidity = humidity;
        this.weather = weather;
        this.temperature = temperature;
        this.pressure = pressure;
        this.flowRate = flowRate;
        this.tick = 0;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(int flowRate) {
        this.flowRate = flowRate;
    }

    public int getTick() {
        return tick;
    }

    public void incrementTick() {
        this.tick++;
    }

}