package org.scb.saga.choreography.wrk.config;

import lombok.RequiredArgsConstructor;
import org.scb.saga.choreography.wrk.events.inventory.InventoryEvent;
import org.scb.saga.choreography.wrk.events.payment.PaymentEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class EventHandlersConfig {

    private final OrderStatusUpdateEventHandler orderEventHandler;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {
        return paymentEvent -> {
            orderEventHandler.updateOrder(paymentEvent.getPayment().getOrderId(), purchaseOrder -> {
                purchaseOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
            });
        };
    }

    @Bean
    public Consumer<InventoryEvent> inventoryEventConsumer(){
        return ie -> {
            orderEventHandler.updateOrder(ie.getInventory().getOrderId(), po -> {
                po.setInventoryStatus(ie.getStatus());
            });
        };
    }
}
