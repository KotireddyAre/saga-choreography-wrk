package org.scb.saga.choreography.wrk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ProductPriceConfig {

    @Bean
    public Map<Integer, Integer> productPrice() {
        return Map.of(
                1, 100,
                2, 200,
                3, 300
        );
    };
}
