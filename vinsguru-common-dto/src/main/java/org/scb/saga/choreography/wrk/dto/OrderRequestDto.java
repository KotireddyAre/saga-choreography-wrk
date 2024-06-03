package org.scb.saga.choreography.wrk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderRequestDto {

    private Integer userId;
    private Integer productId;
    private String orderId;
}
