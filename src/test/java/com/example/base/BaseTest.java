package com.example.base;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseTest {

    private static Network network = Network.newNetwork();

    @ClassRule
    @Container
    public static final GenericContainer MY_SQL_CONTAINER =
            new MySQLContainer()
            .withDatabaseName("arbl")
            .withUsername("arbl")
            .withPassword("arbl")
            .withNetwork(network)
            .withExposedPorts(3306)
            .waitingFor(Wait.forListeningPort());

    @BeforeClass
    public static void startContainers() {
        System.out.println("Started MySQL container");
        System.setProperty("DB_PORT",MY_SQL_CONTAINER.getMappedPort(3306)+"");
        System.setProperty("DB_USER","arbl");
        System.setProperty("DB_PASSWORD","arbl");
    }
}