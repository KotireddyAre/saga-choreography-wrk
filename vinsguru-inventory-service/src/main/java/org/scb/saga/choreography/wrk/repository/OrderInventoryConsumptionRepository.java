package org.scb.saga.choreography.wrk.repository;

import org.scb.saga.choreography.wrk.entity.OrderInventoryConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface OrderInventoryConsumptionRepository extends JpaRepository<OrderInventoryConsumption, String>,
                                                            QuerydslPredicateExecutor<OrderInventoryConsumption> {
}