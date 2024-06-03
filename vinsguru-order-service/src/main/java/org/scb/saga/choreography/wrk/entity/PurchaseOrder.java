package org.scb.saga.choreography.wrk.entity;

import jakarta.persistence.*;
import lombok.*;
import org.scb.saga.choreography.wrk.events.inventory.InventoryStatus;
import org.scb.saga.choreography.wrk.events.order.OrderStatus;
import org.scb.saga.choreography.wrk.events.payment.PaymentStatus;

import java.util.UUID;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    private String id;
    private Integer userId;
    private Integer productId;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    @Version
    private int version;
}
