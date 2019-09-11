package com.example.spring.contexthierarchy.application.hierarchy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.cache.ContextCache;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.spring.contexthierarchy.application.ContextHelper;
import com.example.spring.contexthierarchy.config.AppConfig2;
import com.example.spring.contexthierarchy.config.ConfigOne;
import com.example.spring.contexthierarchy.config.EmptyConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextHierarchy({
        @ContextConfiguration(name = "app", classes = Application.class),
        @ContextConfiguration(name = "config1", classes = ConfigOne.class), // config1 has app as parent
        @ContextConfiguration(name = "config2", classes = AppConfig2.class), // config2 has config1 as parent
        @ContextConfiguration(name = "empty", classes = EmptyConfig.class),
})
public class AppConfig1Config2Test implements ContextHelper {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ContextCache contextCache;


    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

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
        System.out.println(applicationContext.getBean("app"));
        System.out.println(applicationContext.getBean("one"));
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
}
