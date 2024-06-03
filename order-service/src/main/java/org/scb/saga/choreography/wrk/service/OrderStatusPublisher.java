package org.scb.saga.choreography.wrk.service;

import lombok.val;
import org.scb.saga.choreography.wrk.dto.OrderRequestDto;
import org.scb.saga.choreography.wrk.event.OrderEvent;
import org.scb.saga.choreography.wrk.event.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        val orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
