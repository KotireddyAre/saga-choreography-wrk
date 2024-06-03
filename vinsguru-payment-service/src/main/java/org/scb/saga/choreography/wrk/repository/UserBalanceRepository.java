package org.scb.saga.choreography.wrk.repository;

import org.scb.saga.choreography.wrk.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer>,
                                                QuerydslPredicateExecutor<UserBalance> {
}