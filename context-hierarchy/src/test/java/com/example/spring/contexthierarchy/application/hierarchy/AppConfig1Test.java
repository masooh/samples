package com.example.spring.contexthierarchy.application.hierarchy;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.spring.contexthierarchy.config.AppConfig1;
import com.example.spring.contexthierarchy.config.EmptyConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextHierarchy({
        @ContextConfiguration(name = "app", classes = Application.class),
        @ContextConfiguration(name = "config1", classes = AppConfig1.class), // config1 has app as parent
        @ContextConfiguration(name = "empty", classes = EmptyConfig.class), // speed up with empty config as config1 does not getWebMergedContextConfiguration and can be reused
})
public class AppConfig1Test {

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
