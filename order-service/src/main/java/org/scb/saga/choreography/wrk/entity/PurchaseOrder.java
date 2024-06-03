package org.scb.saga.choreography.wrk.entity;

import jakarta.persistence.*;
import lombok.*;
import org.scb.saga.choreography.wrk.event.OrderStatus;
import org.scb.saga.choreography.wrk.event.PaymentStatus;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PURCHASE_ORDER_TBL")
public class PurchaseOrder {

    @Id
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
