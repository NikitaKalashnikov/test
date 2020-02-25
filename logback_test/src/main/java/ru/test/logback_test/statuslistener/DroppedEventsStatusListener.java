package ru.test.logback_test.statuslistener;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.status.WarnStatus;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.test.logback_test.metricpublisher.DroppedEventsMetricPublisher;

import javax.annotation.PostConstruct;

@Component
public class DroppedEventsStatusListener implements StatusListener {

    private static final String DROPPED_EVENT_REGEXP = "Dropped \\d+ \\D+ due to ring buffer at max capacity \\[\\d+]";

    private DroppedEventsMetricPublisher droppedEventsMetricPublisher;

    @Autowired
    public DroppedEventsStatusListener(DroppedEventsMetricPublisher droppedEventsMetricPublisher) {
        this.droppedEventsMetricPublisher = droppedEventsMetricPublisher;
    }

    @PostConstruct
    public void init() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusManager statusManager = loggerContext.getStatusManager();
        statusManager.add(this);
    }

    @Override
    public void addStatusEvent(Status status) {
        if (status.getClass().equals(WarnStatus.class) && status.getMessage().matches(DROPPED_EVENT_REGEXP)) {
            long droppedEventsCount = Long.parseLong(status.getMessage().split(" ")[1]);
            droppedEventsMetricPublisher.publishMetric(droppedEventsCount);
        }
    }
}
