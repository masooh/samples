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

import com.example.spring.contexthierarchy.config.AppConfig2;
import com.example.spring.contexthierarchy.config.AppConfig2Modified;
import com.example.spring.contexthierarchy.config.ConfigOne;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextHierarchy({
        @ContextConfiguration(classes = Application.class),
        @ContextConfiguration(classes = ConfigOne.class), // config1 has app as parent
        @ContextConfiguration(classes = AppConfig2.class), // config2 has config1 as parent
        @ContextConfiguration(classes = AppConfig2Modified.class), // config2 has config1 as parent
})
public class AppConfig1Config2Config2ModifiedTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        beanCount("stringApp");
        beanCount("stringOne");
        beanCount("stringTwo");
        System.out.println(applicationContext.getBean("app"));
        System.out.println(applicationContext.getBean("one"));
        System.out.println(applicationContext.getBean("stringTwo"));
    }

    private void beanCount(String bean) {
        final StringBuilder builder = new StringBuilder();
        builder.append(bean + ": ");
        beanCount(bean, applicationContext, builder);
        System.out.println(builder);
    }

    private void beanCount(String bean, ApplicationContext applicationContext, StringBuilder buffer) {
        long count = Arrays.stream(applicationContext.getBeanDefinitionNames()).filter(name -> name.equals(bean))
                .count();

        buffer.append(applicationContext.getId() + ": " + count);

        if (applicationContext.getParent() != null) {
            buffer.append(", ");
            beanCount(bean, applicationContext.getParent(), buffer);
        }
    }
}
