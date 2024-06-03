package org.scb.saga.choreography.wrk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scb.saga.choreography.wrk.events.order.OrderStatus;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderResponseDto {

    private String orderId;
    private Integer userId;
    private Integer productId;
    private Integer amount;
    private OrderStatus status;
}
