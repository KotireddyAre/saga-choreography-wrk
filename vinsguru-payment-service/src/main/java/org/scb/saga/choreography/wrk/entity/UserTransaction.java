package org.scb.saga.choreography.wrk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "user_transaction_tbl")
@AllArgsConstructor(staticName = "of")
public class UserTransaction {

    @Id
    private String orderId;
    private int userId;
    private int amount;
}
