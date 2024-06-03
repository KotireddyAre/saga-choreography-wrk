package org.scb.saga.choreography.wrk.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class NumberConsumerGateway {

    @Bean
    public Consumer<Long> squareNumbersConsumer() {
        return System.out::println;
    }
}
