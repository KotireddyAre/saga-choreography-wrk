package org.scb.saga.choreography.wrk.service;

import jakarta.transaction.Transactional;
import lombok.val;
import org.scb.saga.choreography.wrk.dto.OrderRequestDto;
import org.scb.saga.choreography.wrk.entity.PurchaseOrder;
import org.scb.saga.choreography.wrk.event.OrderStatus;
import org.scb.saga.choreography.wrk.mapper.PurchaseOrderMapper;
import org.scb.saga.choreography.wrk.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final PurchaseOrderRepository orderRepository;
    private final PurchaseOrderMapper orderMapper;
    private final OrderStatusPublisher publisher;

    public OrderService(PurchaseOrderRepository orderRepository, PurchaseOrderMapper orderMapper,
                                        OrderStatusPublisher publisher) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.publisher = publisher;
    }

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
        val entity = orderMapper.toEntity(orderRequestDto);
        entity.setOrderStatus(OrderStatus.ORDER_CREATED);
        val save = orderRepository.save(entity);
        orderRequestDto.setOrderId(entity.getId());
        publisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
        return save;
    }

    public List<PurchaseOrder> getAllOrders() {
        return orderRepository.findAll();
    }
}
