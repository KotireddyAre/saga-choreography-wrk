package org.scb.saga.choreography.wrk.service;

import lombok.RequiredArgsConstructor;
import org.scb.saga.choreography.wrk.entity.PurchaseOrder;
import org.scb.saga.choreography.wrk.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public List<PurchaseOrder> getAll() {
        return purchaseOrderRepository.findAll();
    }
}
