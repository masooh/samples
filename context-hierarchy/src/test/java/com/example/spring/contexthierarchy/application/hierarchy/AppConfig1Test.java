package com.example.spring.contexthierarchy.application.hierarchy;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring.contexthierarchy.application.ContextHelper;
import com.example.spring.contexthierarchy.application.testbase.empty.EmptyBootApplication;
import com.example.spring.contexthierarchy.config.ConfigOne;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextHierarchy({
        @ContextConfiguration(name = "emptyApp", classes = EmptyBootApplication.class),
        @ContextConfiguration(name = "configOne", classes = ConfigOne.class),
        @ContextConfiguration(name = "app", classes = Application.class)
//        @ContextConfiguration(name = "empty", classes = EmptyConfig.class), // speed up with empty config as config1 does not
        // getWebMergedContextConfiguration and can be reused
})
public class AppConfig1Test implements ContextHelper {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        beanHierarchyStats();
        beanCount("app");
        beanCount("one");
        beanCount("two");
        beanCount("dummyController");
    }

    @Test
    public void dummyControllerPresent() {
        assertNotNull(applicationContext.getBean(DummyController.class));
    }

    @Test
    public void controllerReturnsDummy() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("root")));
    }
}
