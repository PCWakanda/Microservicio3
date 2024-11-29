package com.example.microservicio3.Schedulers;

import com.example.microservicio3.services.WaterSystemController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Component
public class MasterScheduler {

    private static final Logger logger = LoggerFactory.getLogger(MasterScheduler.class);
    private Disposable disposable;
    private final WaterSystemController waterSystemController;

    public MasterScheduler(WaterSystemController waterSystemController) {
        this.waterSystemController = waterSystemController;
    }

    public void startMonitoringFlows() {
        disposable = Flux.interval(Duration.ofSeconds(4))
                .doOnNext(tic -> {
                    logger.info("Tic número: " + tic);
                    waterSystemController.monitorIrrigationSystems();
                    Flux.interval(Duration.ofSeconds(2))
                            .take(1)
                            .doOnNext(innerTic -> {
                                waterSystemController.monitorNetworkSensors();
                            })
                            .subscribeOn(Schedulers.parallel())
                            .subscribe();
                })
                .doOnError(error -> {
                    logger.error("Error en monitoreo: " + error.getMessage(), error);
                })
                .subscribeOn(Schedulers.parallel())
                .subscribe();
        logger.info("Monitoring flow started");
    }

    public void stopMonitoringFlows() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            logger.info("Monitoring flow stopped");
        }
    }
}