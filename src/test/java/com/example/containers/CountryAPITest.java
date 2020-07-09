package com.example.containers;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
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
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import java.io.File;
import java.util.stream.Stream;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
/**
 * Example Test Containers Test . It builds the container "config" on the fly from the
 * dockerfile and use it for the testing.
 *
 */
@Ignore
public class CountryAPITest {
    @Autowired
    private MockMvc mockMvc;
    private static Network network = Network.newNetwork();
    @ClassRule
   @Container
    public static final GenericContainer MY_SQL_CONTAINER = new MySQLContainer().
            withDatabaseName("arbl")
            .withUsername("arbl")
            .withPassword("arbl").withNetwork(network).withExposedPorts(3306).waitingFor(Wait.forListeningPort());
    @ClassRule
    @Container
    public static GenericContainer configServer = new GenericContainer(
            new ImageFromDockerfile("amaraja:configserver")
                    .withFileFromPath(".",
                            new File("C:\\myData\\Amara-raja\\source_code\\" +
                                    "Amararaja\\arbl-services\\arbl-config-server\\").toPath())
                    .withFileFromFile("Dockerfile",new File(
                            "C:\\myData\\Amara-raja\\source_code\\Amararaja\\arbl-services\\" +
                                    "arbl-config-server\\Dockerfile"))).
            withNetwork(network).withExposedPorts(8082);
    @BeforeClass
    public static void startContainers() {
        System.setProperty("CONFIG_PORT",configServer.getMappedPort(8082)+"");
        System.setProperty("DB_PORT",MY_SQL_CONTAINER.getMappedPort(3306)+"");
        System.setProperty("DB_USER","arbl");
        System.setProperty("DB_PASSWORD","arbl");
        Startables.deepStart(Stream.of(MY_SQL_CONTAINER)).join();
        Startables.deepStart(Stream.of(configServer)).join();
    }
    //Ensure that the API test loads fine
/*    @Test
    public void contextLoads() {
        System.out.println();
    }*/
    @Test
    public void getAllCountries() throws Exception {
        this.mockMvc.perform(get("/countries/"))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().string("[{\"id\":1,\"firstName\":\"Foo\",\"lastName\":\"Bar\",\"country\":\"Sweden\"}]"));
    }
}