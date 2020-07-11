package com.example.payroll;

import com.example.base.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EmployeeControllerTest_Junit5 extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void all() throws Exception{
        this.mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(content().string("[{\"id\":1,\"firstName\":\"Foo\",\"lastName\":\"Bar\",\"country\":\"Sweden\"}]"));
    }
}