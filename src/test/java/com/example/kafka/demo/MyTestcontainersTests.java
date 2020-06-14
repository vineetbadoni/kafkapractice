package com.example.kafka.demo;

import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class MyTestcontainersTests {

     // will be shared between test methods
    @ClassRule
    public static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer().
             withDatabaseName("foo")
             .withUsername("foo")
             .withPassword("secret");

    @Test
   public void shudRunMySQLContainer () throws SQLException {

        Connection connection = DriverManager.getConnection(MY_SQL_CONTAINER.getJdbcUrl(),
                MY_SQL_CONTAINER.getUsername(),MY_SQL_CONTAINER.getPassword());

        ResultSet result = connection.createStatement().executeQuery("Select 1");
        result.next();

        int resultInt = result.getInt(1);

        assertEquals(resultInt,1);


    }


}