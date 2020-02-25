package ru.test.logback_test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestLogger {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public void startLogging() throws InterruptedException {
        int count = 0;
        while (true) {
            logger.info("Vasya molodec " + count);
            count++;
            Thread.sleep(1);
        }
    }
}
