package org.scb.saga.choreography.wrk.config;

import jakarta.transaction.Transactional;
import org.scb.saga.choreography.wrk.dto.OrderRequestDto;
import org.scb.saga.choreography.wrk.entity.PurchaseOrder;
import org.scb.saga.choreography.wrk.event.OrderStatus;
import org.scb.saga.choreography.wrk.event.PaymentStatus;
import org.scb.saga.choreography.wrk.repository.PurchaseOrderRepository;
import org.scb.saga.choreography.wrk.service.OrderStatusPublisher;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderStatusUpdateHandler {

    private final PurchaseOrderRepository repository;
    private final OrderStatusPublisher publisher;

    public OrderStatusUpdateHandler(PurchaseOrderRepository repository, OrderStatusPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional
    public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
        repository.findById(id)
                .ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder purchaseOrder) {
        boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isPaymentComplete) {
            publisher.publishOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
        }
    }

    private OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(purchaseOrder.getId());
        orderRequestDto.setUserId(purchaseOrder.getUserId());
        orderRequestDto.setAmount(purchaseOrder.getPrice());
        orderRequestDto.setProductId(purchaseOrder.getProductId());

        return orderRequestDto;
    }

}
