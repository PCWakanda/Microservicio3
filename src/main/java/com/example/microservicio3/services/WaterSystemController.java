package com.example.microservicio3.services;

import com.example.microservicio3.models.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Random;

@Service
public class WaterSystemController {

    private static final Logger logger = LoggerFactory.getLogger(WaterSystemController.class);
    private final Sinks.Many<SensorData> sensorSink = Sinks.many().multicast().onBackpressureBuffer();
    private final RabbitTemplate rabbitTemplate;
    private final Random random = new Random();

    @Autowired
    public WaterSystemController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Flux<SensorData> getSensorDataStream() {
        return sensorSink.asFlux();
    }

    public void monitorNetworkSensors() {
        int pressure = random.nextInt(200) + 1; // Generar presión
        int flowRate = random.nextInt(300) + 1; // Generar flujo

        SensorData sensorData = new SensorData(0, 0, 0, pressure, flowRate);
        sensorSink.tryEmitNext(sensorData);

        // Enviar logs de sensores
        String logMessage = "Datos de sensores: Presión=" + pressure + ", Flujo=" + flowRate;
        logger.info(logMessage); // Usar logger
        rabbitTemplate.convertAndSend("logQueue", formatLogMessage(logMessage));

        // Verificar fallos en sensores de tuberías
        if (pressure < 50 || pressure > 150 || flowRate < 50 || flowRate > 250) {
            String alertMessage = "ALERTA: Fuga o fallo detectado en las tuberías.";
            logger.warn(alertMessage); // Usar logger
            rabbitTemplate.convertAndSend("logQueue", formatLogMessage(alertMessage));
        }
    }

    public void monitorIrrigationSystems() {
        int humidity = random.nextInt(500) + 1;
        int weather = random.nextInt(500) + 1;
        int temperature = random.nextInt(500) + 1;

        String logMessage = "Sensor de humedad: " + humidity + ", Sensor de clima: " + weather + ", Sensor de temperatura: " + temperature;
        logger.info(logMessage); // Usar logger
        rabbitTemplate.convertAndSend("logQueue", formatLogMessage(logMessage));

        if (humidity >= 300 && weather >= 300 && temperature >= 300) {
            String activationMessage = "¡Riego automático activado!";
            logger.info(activationMessage); // Usar logger
            rabbitTemplate.convertAndSend("logQueue", formatLogMessage(activationMessage));
        } else {
            String noActivationMessage = "Riego automático no activado.";
            logger.info(noActivationMessage); // Usar logger
            rabbitTemplate.convertAndSend("logQueue", formatLogMessage(noActivationMessage));
        }
    }

    public boolean testIrrigationActivation() {
        // Simulamos la lectura de los tres sensores
        int humidity = random.nextInt(500) + 1;
        int weather = random.nextInt(500) + 1;
        int temperature = random.nextInt(500) + 1;

        // Verificamos si los tres sensores están entre 300 y 500
        return humidity >= 300 && weather >= 300 && temperature >= 300;
    }

    private String formatLogMessage(String message) {
        return "[INFO] " + message;
    }
}