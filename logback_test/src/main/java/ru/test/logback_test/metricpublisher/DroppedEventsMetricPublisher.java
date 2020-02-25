package ru.test.logback_test.metricpublisher;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DroppedEventsMetricPublisher {

    private static final String LOGBACK_COUNTER_NAME = "logback.events.dropped";
    private static final String LOGBACK_COUNTER_DESCRIPTION = "Count of logback dropped events due to ring buffer overflow";

    private MeterRegistry meterRegistry;

    private Counter counter;

    @Autowired
    public DroppedEventsMetricPublisher(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void initCounter() {
        counter = Counter
                .builder(LOGBACK_COUNTER_NAME)
                .description(LOGBACK_COUNTER_DESCRIPTION)
                .register(meterRegistry);
    }

    public void publishMetric(long droppedEventsCount) {
        counter.increment(droppedEventsCount - counter.count());
    }
}
