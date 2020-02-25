package ru.test.logback_test.statuslistener;

import ch.qos.logback.core.status.WarnStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.test.logback_test.metricpublisher.DroppedEventsMetricPublisher;

import static org.mockito.Mockito.*;

class DroppedEventsStatusListenerTest {

    private DroppedEventsMetricPublisher droppedEventsMetricPublisher;
    private DroppedEventsStatusListener droppedEventsStatusListener;

    @BeforeEach
    public void init() {
        droppedEventsMetricPublisher = mock(DroppedEventsMetricPublisher.class);
        doNothing().when(droppedEventsMetricPublisher).publishMetric(anyInt());
        droppedEventsStatusListener = new DroppedEventsStatusListener(droppedEventsMetricPublisher);
    }

    @Test
    public void addStatusEventTest() {
        WarnStatus warnStatus = new WarnStatus("Dropped 666 events (and counting...) due to ring buffer at max capacity [65536]", new Object());
        droppedEventsStatusListener.addStatusEvent(warnStatus);
        verify(droppedEventsMetricPublisher, times(1)).publishMetric(666);

        warnStatus = new WarnStatus("Dropped 6546546 total events due to ring buffer at max capacity [65536]", new Object());
        droppedEventsStatusListener.addStatusEvent(warnStatus);
        verify(droppedEventsMetricPublisher, times(1)).publishMetric(6546546);
    }


}