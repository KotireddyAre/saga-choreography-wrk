package org.scb.saga.choreography.wrk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class KafkaNumberProducerApp {

    public static void main(String[] args) {
        log.info("Starting Kafka Number Producer Application...");

        new SpringApplicationBuilder(KafkaNumberProducerApp.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}