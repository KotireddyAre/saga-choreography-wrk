package org.scb.saga.choreography.wrk.repository;

import org.scb.saga.choreography.wrk.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, String>,
                                                QuerydslPredicateExecutor<UserTransaction> {
}