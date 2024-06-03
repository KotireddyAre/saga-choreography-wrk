package org.scb.saga.choreography.wrk;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
/*@OpenAPIDefinition(
        info = @Info(
                title = "Order Service API Specification",
                version = "1.0",
                description = "Documentation for Order Service API v1.0"
        )
)*/
public class VinsguruOrderServiceApp {

    public static void main(String[] args) {
        log.info("Starting Vinsguru Order Service Application...");

        new SpringApplicationBuilder(VinsguruOrderServiceApp.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}