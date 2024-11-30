package com.example.microservicio3;

import com.example.microservicio3.Schedulers.MasterScheduler;
import com.example.microservicio3.models.SensorDataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Microservicio3Application {

    @Autowired
    private MasterScheduler masterScheduler;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public static void main(String[] args) {
        SpringApplication.run(Microservicio3Application.class, args);
    }

    @PostConstruct
    public void init() {
        sensorDataRepository.deleteAll();
        masterScheduler.startMonitoringFlows();
    }
}