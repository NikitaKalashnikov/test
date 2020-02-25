package ru.test.logback_test.jmx;

import ru.test.logback_test.logback.DroppedEventsStatusListener;

public class LogbackDroppedEvents implements LogbackDroppedEventsMBean {

    private DroppedEventsStatusListener droppedEventsStatusListener;

    public LogbackDroppedEvents(DroppedEventsStatusListener droppedEventsStatusListener) {
        this.droppedEventsStatusListener = droppedEventsStatusListener;
    }

    @Override
    public String getDroppedEventsMessage() {
        return droppedEventsStatusListener.getStatusMessage();
    }
}