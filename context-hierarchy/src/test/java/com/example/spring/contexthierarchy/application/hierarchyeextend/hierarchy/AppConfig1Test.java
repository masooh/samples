package com.example.spring.contexthierarchy.application.hierarchyeextend.hierarchy;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class AppConfig1Test extends TestBase {

    @Autowired
    ApplicationContext applicationContext;

    //    @Autowired
    //    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        beanCount("stringApp");
        beanCount("stringOne");
        beanCount("stringTwo");
    }

    //    @Test
    //    public void controllerReturnsDummy() throws Exception {
    //        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
    //                .andExpect(content().string(containsString("root")));
    //    }

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
