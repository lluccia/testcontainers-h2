package com.github.lluccia.testcontainers;

import com.github.dockerjava.api.command.InspectContainerResponse;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;
import java.nio.file.Path;

public class H2Container extends GenericContainer<H2Container> {

    private static final String IMAGE_NAME = "eclipse-temurin:21-jre-alpine";
    private static final String H2_JAR_PATH = "/h2/h2.jar";

    public H2Container() {
        super(IMAGE_NAME);

        String homeDir = System.getenv("HOME");

        withCopyFileToContainer(
                MountableFile.forHostPath(
                        Path.of(homeDir, ".m2/repository/com/h2database/h2/2.1.214/h2-2.1.214.jar"))
                , H2_JAR_PATH);

        withCopyFileToContainer(
                MountableFile.forClasspathResource("init.sql"), "/h2/init.sql");

        withCommand("/opt/java/openjdk/bin/java",
                "-cp", H2_JAR_PATH,
                "org.h2.tools.Server",
                "-tcp", "-tcpAllowOthers",
                "-web", "-webAllowOthers"
        );
    }

    @Override
    protected void containerIsStarted(InspectContainerResponse containerInfo) {
        try {
            execInContainer("/opt/java/openjdk/bin/java",
                    "-cp", H2_JAR_PATH,
                    "org.h2.tools.RunScript",
                    "-url", "jdbc:h2:~/test;DB_CLOSE_DELAY=-1",
                    "-script", "/h2/init.sql"
            );
        } catch (IOException|InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
