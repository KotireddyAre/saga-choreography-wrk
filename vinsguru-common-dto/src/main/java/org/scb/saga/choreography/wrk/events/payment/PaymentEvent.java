package org.scb.saga.choreography.wrk.events.payment;

import lombok.Builder;
import lombok.Data;
import org.scb.saga.choreography.wrk.dto.PaymentDto;
import org.scb.saga.choreography.wrk.events.Event;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class PaymentEvent implements Event {

    private final String eventId = UUID.randomUUID().toString();
    private final Date date = new Date();
    private PaymentDto payment;
    private PaymentStatus paymentStatus;

    public PaymentEvent() {}

    public PaymentEvent(PaymentDto payment, PaymentStatus paymentStatus) {
        this.payment = payment;
        this.paymentStatus = paymentStatus;
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
