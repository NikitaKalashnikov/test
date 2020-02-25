package ru.test.logback_test.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.status.WarnStatus;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DroppedEventsStatusListener implements StatusListener {

    private static final String LOGBACK_COUNTER_NAME = "logback.dropped_events.counter";
    private static final String DROPPED_EVENT_REGEXP = "Dropped \\d+ events \\(and counting...\\) due to ring buffer at max capacity \\[\\d+]";

    @Autowired
    private MeterRegistry meterRegistry;

    private String statusMessage = "No dropped events for now";
    private int droppedEventsCount;
    private boolean registered;

    public void register() {
        if (registered) {
            return;
        }

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusManager statusManager = loggerContext.getStatusManager();
        statusManager.add(this);

        registered = true;
    }

    @Override
    public void addStatusEvent(Status status) {
        if (status.getClass().equals(WarnStatus.class) && status.getMessage().matches(DROPPED_EVENT_REGEXP)) {
            String message = status.getMessage();
            statusMessage = message;
            droppedEventsCount = Integer.parseInt(message.split(" ")[1]);
        }

        publishMetric();
    }

    private void publishMetric() {
        Counter counter = Counter.builder(LOGBACK_COUNTER_NAME).register(meterRegistry);
        counter.increment(droppedEventsCount - counter.count());
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public int getDroppedEventsCount() {
        return this.droppedEventsCount;
    }
}
