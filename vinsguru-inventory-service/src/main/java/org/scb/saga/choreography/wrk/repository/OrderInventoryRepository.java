package org.scb.saga.choreography.wrk.repository;

import org.scb.saga.choreography.wrk.entity.OrderInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderInventoryRepository extends JpaRepository<OrderInventory, Integer>,
                                                    QuerydslPredicateExecutor<OrderInventory> {
}