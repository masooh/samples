package com.example.spring.contexthierarchy.application.flat;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.contexthierarchy.application.hierarchy.Application;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class AppMockMvcTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        beanCount("stringApp");
        beanCount("stringOne");
        beanCount("stringTwo");
    }

    @Test
    public void controllerReturnsDummy() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("root")));
    }

    private void beanCount(String bean) {
        final StringBuilder builder = new StringBuilder();
        beanCount(bean, applicationContext, builder);
        System.out.println(builder);
    }

    private void beanCount(String bean, ApplicationContext applicationContext, StringBuilder buffer) {
        long count = Arrays.stream(applicationContext.getBeanDefinitionNames()).filter(name -> name.equals(bean))
                .count();

        buffer.append(bean + ": " + count);

        if (applicationContext.getParent() != null) {
            buffer.append(", ");
            beanCount(bean, applicationContext.getParent(), buffer);
        }
    }
}
