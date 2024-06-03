package org.scb.saga.choreography.wrk.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.scb.saga.choreography.wrk.entity.PurchaseOrder;
import org.scb.saga.choreography.wrk.events.inventory.InventoryStatus;
import org.scb.saga.choreography.wrk.events.order.OrderStatus;
import org.scb.saga.choreography.wrk.events.payment.PaymentStatus;
import org.scb.saga.choreography.wrk.repository.PurchaseOrderRepository;
import org.scb.saga.choreography.wrk.service.OrderStatusPublisher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderStatusUpdateEventHandler {

    private final PurchaseOrderRepository orderRepository;
    private final OrderStatusPublisher publisher;

    @Transactional
    public void updateOrder(final String id, Consumer<PurchaseOrder> consumer){
        this.orderRepository
                .findById(id)
                .ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder purchaseOrder) {
        if(Objects.isNull(purchaseOrder.getInventoryStatus()) || Objects.isNull(purchaseOrder.getPaymentStatus()))
            return;
        var isComplete = PaymentStatus.RESERVED.equals(purchaseOrder.getPaymentStatus())
                && InventoryStatus.RESERVED.equals(purchaseOrder.getInventoryStatus());
        var orderStatus = isComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isComplete){
            this.publisher.raiseOrderEvent(purchaseOrder, orderStatus);
        }
    }
}
