package com.example.base;

import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Method;

@Testcontainers
public class ZerocodeSpringBootRunner extends ZeroCodeUnitRunner {

    private static Network network = Network.newNetwork();

    public static final GenericContainer MY_SQL_CONTAINER;
    //All the containers should be started only once.
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

    public static boolean appRunning = false;

    public ZerocodeSpringBootRunner(Class<?> klass) throws Exception {
        super(klass);

        if(!appRunning){
            Method mainClassMethod = klass.getMethod("getMainClass");
            String classNameString = (String)mainClassMethod.invoke(null);
            final Object[] args = new Object[1];
            args[0] = new String[] { };
            Class.forName(classNameString).getMethod("main",String[].class).invoke(null, args);
            appRunning = true;
        }
    }
}
