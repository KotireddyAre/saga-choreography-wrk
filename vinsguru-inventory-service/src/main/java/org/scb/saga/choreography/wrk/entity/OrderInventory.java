package org.scb.saga.choreography.wrk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderInventory {

    @Id
    private int productId;
    private int availableInventory;
}
