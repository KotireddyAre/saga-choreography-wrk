package org.scb.saga.choreography.wrk.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.scb.saga.choreography.wrk.dto.InventoryDto;
import org.scb.saga.choreography.wrk.entity.OrderInventoryConsumption;
import org.scb.saga.choreography.wrk.events.inventory.InventoryEvent;
import org.scb.saga.choreography.wrk.events.inventory.InventoryStatus;
import org.scb.saga.choreography.wrk.events.order.OrderEvent;
import org.scb.saga.choreography.wrk.repository.OrderInventoryConsumptionRepository;
import org.scb.saga.choreography.wrk.repository.OrderInventoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final OrderInventoryRepository orderInventoryRepository;
    private final OrderInventoryConsumptionRepository consumptionRepository;

    @Transactional
    public InventoryEvent newOrderInventory(OrderEvent orderEvent) {
        val inventoryDto = InventoryDto.of(orderEvent.getPurchaseOrder()
                .getOrderId(), orderEvent.getPurchaseOrder().getProductId());
        return orderInventoryRepository.findById(orderEvent.getPurchaseOrder().getProductId())
                .filter(orderInventory -> orderInventory.getAvailableInventory() > 0)
                .map(orderInventory -> {
                    orderInventory.setAvailableInventory(orderInventory.getAvailableInventory() - 1);
                    consumptionRepository.save(OrderInventoryConsumption.of(orderEvent.getPurchaseOrder().getOrderId(),
                            orderEvent.getPurchaseOrder().getProductId(), 1));
                    return new InventoryEvent(inventoryDto, InventoryStatus.RESERVED);
                })
                .orElse(new InventoryEvent(inventoryDto, InventoryStatus.REJECTED));
    }

    @Transactional
    public void cancelOrderInventory(OrderEvent orderEvent) {
        consumptionRepository.findById(orderEvent.getPurchaseOrder().getOrderId())
                .ifPresent(ci -> {
                    orderInventoryRepository.findById(ci.getProductId())
                            .ifPresent(i ->
                                    i.setAvailableInventory(i.getAvailableInventory() + ci.getQuantityConsumed())
                            );
                    consumptionRepository.delete(ci);
                });
    }
}
