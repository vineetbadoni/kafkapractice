package com.example.payroll;

import com.example.base.ZerocodeSpringBootRunner;
import org.jsmart.zerocode.core.domain.Scenario;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
@TargetEnv("application_host.properties")
@RunWith(ZerocodeSpringBootRunner.class)
public class EmployeeControllerTest_With_BDD_REST_Mocks_2 {
    @Test
    @Scenario("integration_tests/country/get/get_all_employees_by_id_test.json")
    public void all() throws Exception{}

    public static String getMainClass(){
        return "com.example.DemoApplication";
    }
}