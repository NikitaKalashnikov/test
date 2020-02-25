package ru.test.logback_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.logback_test.jmx.LogbackDroppedEventsJmxService;
import ru.test.logback_test.logback.DroppedEventsStatusListener;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;

@RestController
public class TestController {

    @Autowired
    LogbackDroppedEventsJmxService logbackDroppedEventsJmxService;

    @Autowired
    TestLogger testLogger;

    @Autowired
    DroppedEventsStatusListener droppedEventsStatusListener;

    private volatile boolean isLogging = false;
    private boolean jmxStarted = false;

    @GetMapping
    public String test() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {

//        if (!jmxStarted) {
//            logbackDroppedEventsJmxService.registerJmx();
//            jmxStarted = true;
//        }

        droppedEventsStatusListener.register();

        if (!isLogging) {
            new Thread(() -> {
                try {
                    isLogging = true;
                    testLogger.startLogging();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        return "Logging started";
    }

}