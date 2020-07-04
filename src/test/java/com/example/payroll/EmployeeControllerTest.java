package com.example.payroll;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static Network network = Network.newNetwork();
    @ClassRule
    @Container
    public static final GenericContainer MY_SQL_CONTAINER = new MySQLContainer().
            withDatabaseName("arbl")
            .withUsername("arbl")
            .withPassword("arbl").withNetwork(network).withExposedPorts(3306).waitingFor(Wait.forListeningPort());

    @BeforeClass
    public static void startContainers() {
        System.setProperty("DB_PORT",MY_SQL_CONTAINER.getMappedPort(3306)+"");
        System.setProperty("DB_USER","arbl");
        System.setProperty("DB_PASSWORD","arbl");
        Startables.deepStart(Stream.of(MY_SQL_CONTAINER)).join();
    }

    @Test
    public void all() throws Exception{
        startContainers();
        this.mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string("[{\"id\":1,\"firstName\":\"Foo\",\"lastName\":\"Bar\",\"country\":\"Sweden\"}]"));
    }

    //@Test
    public void newEmployee() {
    }

    //@Test
    public void one() {
    }

    //@Test
    public void replaceEmployee() {
    }

    //@Test
    public void deleteEmployee() {
    }
}