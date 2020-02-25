package ru.test.logback_test.logback;

import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.WarnStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroppedEventsStatusListenerTest {

    private static final String TEST_MESSAGE = "Dropped 666 events (and counting...) due to ring buffer at max capacity [65536]";

    @Test
    void getStatusMessage() {
        DroppedEventsStatusListener droppedEventsStatusListener = new DroppedEventsStatusListener();
        Status statusEvent = new WarnStatus(TEST_MESSAGE, new Object());
        droppedEventsStatusListener.addStatusEvent(statusEvent);
        assertEquals(TEST_MESSAGE, droppedEventsStatusListener.getStatusMessage());
    }
}