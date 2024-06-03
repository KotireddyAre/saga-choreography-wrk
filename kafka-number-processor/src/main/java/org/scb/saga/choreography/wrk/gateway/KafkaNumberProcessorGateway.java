package org.scb.saga.choreography.wrk.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.function.Function;

@Configuration
public class KafkaNumberProcessorGateway {

    @Bean
    public Function<Long, Long> evenNumberSquareProcessor() {
        return input -> Optional.of(input)
                .filter(i -> i % 2 == 0)
                .map(num -> num * num)
                .orElseGet(() -> 0L);
    }
}
