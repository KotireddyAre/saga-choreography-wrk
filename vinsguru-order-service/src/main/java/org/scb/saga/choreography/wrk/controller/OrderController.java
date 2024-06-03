package org.scb.saga.choreography.wrk.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.scb.saga.choreography.wrk.dto.OrderRequestDto;
import org.scb.saga.choreography.wrk.entity.PurchaseOrder;
import org.scb.saga.choreography.wrk.service.OrderCommandService;
import org.scb.saga.choreography.wrk.service.OrderQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@SecurityRequirement(name = "basicAuth")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDto requestDTO){
        requestDTO.setOrderId(UUID.randomUUID().toString());
        return this.orderCommandService.createOrder(requestDTO);
    }

    @GetMapping("/all")
    public List<PurchaseOrder> getOrders(){
        return this.orderQueryService.getAll();
    }
}
