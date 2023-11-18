package com.github.lluccia.testcontainers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class H2ContainerTest {

    private static Logger logger = LoggerFactory.getLogger("tc.h2");

    @Container
    public H2Container h2Container = new H2Container();

    @BeforeEach
    void setup() {
        h2Container.withLogConsumer(new Slf4jLogConsumer(logger));
    }

    @Test
    void canCreateGenericContainer() {
        assertTrue(h2Container.isCreated());
    }
}

