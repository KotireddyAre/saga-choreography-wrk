package org.scb.saga.choreography.wrk.repository;

import org.scb.saga.choreography.wrk.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer>,
                                                    QuerydslPredicateExecutor<PurchaseOrder> {
}