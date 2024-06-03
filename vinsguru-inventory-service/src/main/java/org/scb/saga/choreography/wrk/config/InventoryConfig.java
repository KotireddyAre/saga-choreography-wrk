package org.scb.saga.choreography.wrk.config;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.scb.saga.choreography.wrk.events.inventory.InventoryEvent;
import org.scb.saga.choreography.wrk.events.order.OrderEvent;
import org.scb.saga.choreography.wrk.events.order.OrderStatus;
import org.scb.saga.choreography.wrk.service.InventoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class InventoryConfig {

    private final InventoryService inventoryService;

    @Bean
    public Function<Flux<OrderEvent>, Flux<InventoryEvent>> inventoryProcess() {
        return orderEventFlux -> orderEventFlux.flatMap(this::processInventory);
    }

    private Mono<InventoryEvent> processInventory(OrderEvent orderEvent) {
        if (orderEvent.getOrderStatus().equals(OrderStatus.ORDER_CREATED)) {
            return Mono.fromSupplier(() -> this.inventoryService.newOrderInventory(orderEvent));
        } else {
            return Mono.fromRunnable(() -> this.inventoryService.cancelOrderInventory(orderEvent));
        }
    }
}
