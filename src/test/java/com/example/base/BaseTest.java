package com.example.base;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class BaseTest {

    private static Network network = Network.newNetwork();

    public static final GenericContainer MY_SQL_CONTAINER;
    static {
        MY_SQL_CONTAINER = new MySQLContainer()
                .withDatabaseName("arbl")
                .withUsername("arbl")
                .withPassword("arbl")
                .withNetwork(network)
                .withExposedPorts(3306);

        //Start the container. Blocking call.
        MY_SQL_CONTAINER.start();

        //Set the parameters required to connect to MySQL.
        System.setProperty("DB_PORT",MY_SQL_CONTAINER.getMappedPort(3306)+"");
        System.setProperty("DB_USER","arbl");
        System.setProperty("DB_PASSWORD","arbl");
    }
}