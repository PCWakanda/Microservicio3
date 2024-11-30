package com.example.microservicio3.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "logQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}