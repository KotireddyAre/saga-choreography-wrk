package org.scb.saga.choreography.wrk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class OrderServiceApp {

    public static void main(String[] args) {
        log.info("Starting Order Service Application...");

        new SpringApplicationBuilder(OrderServiceApp.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
