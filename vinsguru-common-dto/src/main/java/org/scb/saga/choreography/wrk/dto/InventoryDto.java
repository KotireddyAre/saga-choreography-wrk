package org.scb.saga.choreography.wrk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class InventoryDto {

    private String orderId;
    private Integer productId;
}
