package com.example.microservicio3.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue logQueue() {
        return new Queue("microservicio3LogQueue", false);
    }
}
