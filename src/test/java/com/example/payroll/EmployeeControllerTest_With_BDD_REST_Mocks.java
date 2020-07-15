package com.example.payroll;

import com.example.DemoApplication;
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
public class EmployeeControllerTest_With_BDD_REST_Mocks {
    @Test
    @Scenario("integration_tests/get/get_new_customer_by_id_test.json")
    public void all() throws Exception{}

    public static String getMainClass(){
        return "com.example.DemoApplication";
    }
}