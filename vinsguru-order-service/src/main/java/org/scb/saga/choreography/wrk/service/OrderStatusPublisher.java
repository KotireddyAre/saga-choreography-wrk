package org.scb.saga.choreography.wrk.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.scb.saga.choreography.wrk.dto.PurchaseOrderDto;
import org.scb.saga.choreography.wrk.entity.PurchaseOrder;
import org.scb.saga.choreography.wrk.events.order.OrderEvent;
import org.scb.saga.choreography.wrk.events.order.OrderStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class OrderStatusPublisher {

    private final Sinks.Many<OrderEvent> orderSinks;

    public void raiseOrderEvent(PurchaseOrder purchaseOrder, OrderStatus orderStatus) {
        val orderDto = PurchaseOrderDto.builder()
                .orderId(purchaseOrder.getId())
                .productId(purchaseOrder.getProductId())
                .userId(purchaseOrder.getUserId())
                .price(purchaseOrder.getPrice())
                .build();
        val orderEvent = new OrderEvent(orderDto, orderStatus);
        val emitResult = this.orderSinks.tryEmitNext(orderEvent);
    }
}
