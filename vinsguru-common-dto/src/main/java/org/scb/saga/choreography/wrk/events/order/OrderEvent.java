package org.scb.saga.choreography.wrk.events.order;

import lombok.Builder;
import lombok.Data;
import org.scb.saga.choreography.wrk.dto.PurchaseOrderDto;
import org.scb.saga.choreography.wrk.events.Event;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class OrderEvent implements Event {

    private final String eventId = UUID.randomUUID().toString();
    private final Date date = new Date();
    private PurchaseOrderDto purchaseOrder;
    private OrderStatus orderStatus;

    public OrderEvent() {
    }

    public OrderEvent(PurchaseOrderDto purchaseOrder, OrderStatus orderStatus) {
        this.purchaseOrder = purchaseOrder;
        this.orderStatus = orderStatus;
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
