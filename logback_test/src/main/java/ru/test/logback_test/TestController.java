package ru.test.logback_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.logback_test.statuslistener.DroppedEventsStatusListener;

@RestController
public class TestController {

    @Autowired
    TestLogger testLogger;

    private volatile boolean isLogging = false;
    private boolean jmxStarted = false;

    @GetMapping
    public String test() {
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