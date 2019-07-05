package com.example.spring.contexthierarchy.application.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.example.spring.contexthierarchy.config.AppConfig2;
import com.example.spring.contexthierarchy.config.EmptyConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextHierarchy({
        @ContextConfiguration(name = "app", classes = Application.class),
        @ContextConfiguration(name = "config1", classes = AppConfig1.class), // config1 has app as parent
        @ContextConfiguration(name = "config2", classes = AppConfig2.class), // config2 has config1 as parent
        @ContextConfiguration(name = "empty", classes = EmptyConfig.class),
})
public class AppConfig1Config2Test {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    DummyController dummyController;

//    @LocalServerPort
//    private int port;

//    @Autowired
//    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        beanCount("stringApp");
        beanCount("stringOne");
        beanCount("stringTwo");
        System.out.println(applicationContext.getBean("stringApp"));
        System.out.println(applicationContext.getBean("stringOne"));
        System.out.println(applicationContext.getBean("stringTwo"));
    }

    @Test
    public void controllerPresent() {
        assertThat(dummyController).isNotNull();
    }

//    @Test
//    public void greetingShouldReturnDefaultMessage() throws Exception {
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
//                String.class)).contains("Hello World");
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
