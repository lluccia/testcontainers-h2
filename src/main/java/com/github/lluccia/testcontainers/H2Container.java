package com.github.lluccia.testcontainers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.MountableFile;

public class H2Container extends GenericContainer<H2Container> {

    private static final String IMAGE_NAME = "eclipse-temurin:21-jre-alpine";

    public H2Container() {
        super(IMAGE_NAME);

        withCommand("sleep 1d");
    }
}
