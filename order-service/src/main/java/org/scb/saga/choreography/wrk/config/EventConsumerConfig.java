package org.scb.saga.choreography.wrk.config;

import org.scb.saga.choreography.wrk.event.PaymentEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {

    private final OrderStatusUpdateHandler handler;

    public EventConsumerConfig(OrderStatusUpdateHandler handler) {
        this.handler = handler;
    }

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {
        return paymentEvent -> handler.updateOrder(paymentEvent.getPaymentRequestDto().getOrderId(), purchaseOrder -> {
            purchaseOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
        });
    }
}
