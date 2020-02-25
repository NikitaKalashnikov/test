package ru.test.logback_test.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import ru.test.logback_test.logback.DroppedEventsStatusListener;

public class MetricPublisher {

    private MeterRegistry meterRegistry;
    private DroppedEventsStatusListener droppedEventsStatusListener;

    @Autowired
    public MetricPublisher(MeterRegistry meterRegistry, DroppedEventsStatusListener droppedEventsStatusListener) {
        this.meterRegistry = meterRegistry;

        this.droppedEventsStatusListener = droppedEventsStatusListener;
        this.droppedEventsStatusListener.register();
    }

    public void publishMetric() {
//        Counter counter = meterRegistry.counter("droppedEventsCounter");
//        int valueToIncrement = droppedEventsStatusListener.getDroppedEventsCount() - (int) counter.count();
//        counter.increment(valueToIncrement);

        meterRegistry.gauge("droppedEventsCount", droppedEventsStatusListener.getDroppedEventsCount());
    }

}
