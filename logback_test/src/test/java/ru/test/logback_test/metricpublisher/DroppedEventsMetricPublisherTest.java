package ru.test.logback_test.metricpublisher;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import static org.mockito.Mockito.*;

class DroppedEventsMetricPublisherTest {

    private Counter counter;

    private DroppedEventsMetricPublisher droppedEventsMetricPublisher;

    @BeforeEach
    public void init() throws NoSuchFieldException {
        counter = mock(PrometheusCounter.class);
        droppedEventsMetricPublisher = new DroppedEventsMetricPublisher(mock(MeterRegistry.class));
        FieldSetter.setField(droppedEventsMetricPublisher, droppedEventsMetricPublisher.getClass().getDeclaredField("counter"), counter);
    }

    @Test
    void publishMetricTest() {
        when(counter.count()).thenReturn(0d);
        droppedEventsMetricPublisher.publishMetric(100);
        verify(counter, times(1)).increment(100);

        when(counter.count()).thenReturn(100d);
        droppedEventsMetricPublisher.publishMetric(1000);
        verify(counter, times(1)).increment(900);
    }
}