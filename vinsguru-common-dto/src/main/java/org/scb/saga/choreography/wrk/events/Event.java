package org.scb.saga.choreography.wrk.events;

import java.util.Date;
import java.util.UUID;

public interface Event {

    String getEventId();
    Date getDate();
}
