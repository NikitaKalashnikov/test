package ru.test.logback_test;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;

public class Main {

    private static final String DROPPED_EVENT_REGEXP = "Dropped \\d+ events \\(and counting...\\) due to ring buffer at max capacity \\[\\d+]";

    public static void main(String[] args) {
        String s1 = "Dropped 23 events (and counting...) due to ring buffer at max capacity [65596]";

        System.out.println(s1.matches(DROPPED_EVENT_REGEXP));
    }
}
