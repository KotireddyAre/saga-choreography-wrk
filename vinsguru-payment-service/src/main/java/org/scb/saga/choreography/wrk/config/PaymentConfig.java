package org.scb.saga.choreography.wrk.config;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.scb.saga.choreography.wrk.events.order.OrderEvent;
import org.scb.saga.choreography.wrk.events.order.OrderStatus;
import org.scb.saga.choreography.wrk.events.payment.PaymentEvent;
import org.scb.saga.choreography.wrk.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class PaymentConfig {

    private final PaymentService paymentService;

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
