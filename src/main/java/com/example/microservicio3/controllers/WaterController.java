package com.example.microservicio3.controllers;

import com.example.microservicio3.models.SensorData; // Asegúrate de que este paquete sea correcto
import com.example.microservicio3.services.WaterSystemController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class WaterController {

    private final WaterSystemController waterSystemController;

    @Autowired
    public WaterController(WaterSystemController waterSystemController) {
        this.waterSystemController = waterSystemController;
    }

    /**
     * Endpoint para obtener el flujo de datos en tiempo real de los sensores de agua.
     *
     * @return Flujo de datos (Reactor Flux)
     */
    @GetMapping("/sensors/data")
    public Flux<SensorData> getSensorData() {
        return waterSystemController.getSensorDataStream();
    }

    /**
     * Endpoint para iniciar un monitoreo manual de sensores de red de agua.
     */
    @GetMapping("/sensors/monitor")
    public String startMonitoring() {
        waterSystemController.monitorNetworkSensors();
        return "Monitoreo de sensores iniciado";
    }

    /**
     * Endpoint para iniciar un monitoreo manual de sistemas de riego.
     */
    @GetMapping("/irrigation/monitor")
    public String startIrrigationMonitoring() {
        waterSystemController.monitorIrrigationSystems();
        return "Monitoreo de sistemas de riego iniciado. Revisa los logs para verificar la activación del riego.";
    }

    /**
     * Endpoint para probar la lógica de activación del riego automático.
     *
     * @return Mensaje con el estado del riego.
     */
    @GetMapping("/irrigation/test")
    public String testIrrigationSystem() {
        boolean isActivated = waterSystemController.testIrrigationActivation();
        return isActivated ? "¡El sistema de riego fue activado!" : "El sistema de riego no se activó.";
    }
}