package com.example.task;

import com.example.task.rest.controllerImpl.Controller;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class TaskApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
    }


    @Test
    void Test1() throws Exception {
        mockMvc.perform(get("/Add")
                .param("intA", "3").param("intB", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"addResult\":5"))).andDo(print());
    }

    @Test
    void Test2() throws Exception {
        mockMvc.perform(get("/Divine")
                .param("intA", "3").param("intB", "0"))
                .andExpect(status().isBadRequest()).andDo(print());
    }

}
