package org.scb.saga.choreography.wrk.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.scb.saga.choreography.wrk.dto.OrderRequestDto;
import org.scb.saga.choreography.wrk.entity.PurchaseOrder;
import org.scb.saga.choreography.wrk.events.order.OrderStatus;
import org.scb.saga.choreography.wrk.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderCommandService {

    private final PurchaseOrderRepository orderRepository;
    private final OrderStatusPublisher publisher;

    @Autowired
    private Map<Integer, Integer> productPriceMap;

    public PurchaseOrder createOrder(OrderRequestDto requestDTO) {
        val save = orderRepository.save(toEntity(requestDTO));
        publisher.raiseOrderEvent(save, OrderStatus.ORDER_CREATED);
        return save;
    }

    private PurchaseOrder toEntity(final OrderRequestDto requestDTO) {
        return PurchaseOrder.builder()
                .id(requestDTO.getOrderId().toString())
                .productId(requestDTO.getProductId())
                .userId(requestDTO.getUserId())
                .orderStatus(OrderStatus.ORDER_CREATED)
                .price(productPriceMap.get(requestDTO.getProductId()))
                .build();
    }
}
