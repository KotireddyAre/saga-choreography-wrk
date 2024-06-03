package org.scb.saga.choreography.wrk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class VinsguruPaymentServiceApp {

    public static void main(String[] args) {
        log.info("Starting Vinsguru Payment Service Application...");

        new SpringApplicationBuilder(VinsguruPaymentServiceApp.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}