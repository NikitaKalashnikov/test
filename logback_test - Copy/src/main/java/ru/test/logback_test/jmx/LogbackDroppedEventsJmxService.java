package ru.test.logback_test.jmx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.logback_test.logback.DroppedEventsStatusListener;

import javax.management.*;
import java.lang.management.ManagementFactory;

@Service
public class LogbackDroppedEventsJmxService {

    private static final String JMX_OBJECT_NAME = "LogbackDroppedEvents:type=LogbackDroppedEvents";

    private DroppedEventsStatusListener droppedEventsStatusListener;

    @Autowired
    public LogbackDroppedEventsJmxService(DroppedEventsStatusListener droppedEventsStatusListener) {
        this.droppedEventsStatusListener = droppedEventsStatusListener;
    }

    public void registerJmx() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName(JMX_OBJECT_NAME);

        droppedEventsStatusListener.register();

        LogbackDroppedEvents mBean = new LogbackDroppedEvents(droppedEventsStatusListener);
        server.registerMBean(mBean, objectName);
    }
}
