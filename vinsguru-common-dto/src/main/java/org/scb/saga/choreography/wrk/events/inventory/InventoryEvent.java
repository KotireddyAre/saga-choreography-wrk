package org.scb.saga.choreography.wrk.events.inventory;

import lombok.Data;
import org.scb.saga.choreography.wrk.dto.InventoryDto;
import org.scb.saga.choreography.wrk.events.Event;

import java.util.Date;
import java.util.UUID;

@Data
public class InventoryEvent implements Event {

    private final String eventId = UUID.randomUUID().toString();
    private final Date date = new Date();
    private InventoryDto inventory;
    private InventoryStatus status;

    public InventoryEvent() {
    }

    public InventoryEvent(InventoryDto inventory, InventoryStatus status) {
        this.inventory = inventory;
        this.status = status;
    }

    @Override
    public String getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.date;
    }
}
