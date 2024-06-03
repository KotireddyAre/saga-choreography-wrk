package org.scb.saga.choreography.wrk.config;

import org.scb.saga.choreography.wrk.event.OrderEvent;
import org.scb.saga.choreography.wrk.event.OrderStatus;
import org.scb.saga.choreography.wrk.event.PaymentEvent;
import org.scb.saga.choreography.wrk.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class PaymentConsumerConfig {

    private final PaymentService paymentService;

    public PaymentConsumerConfig(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Bean
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {

        if (orderEvent.getOrderStatus().equals(OrderStatus.ORDER_CREATED)) {
            return Mono.fromSupplier(() -> this.paymentService.newOrderEvent(orderEvent));
        } else {
            return Mono.fromRunnable(() -> this.paymentService.cancelOrderEvent(orderEvent));
        }
    }
}

