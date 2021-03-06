package com.example.spring.contexthierarchy.application.hierarchywithinheritance;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.spring.contexthierarchy.application.hierarchy.Application;
import com.example.spring.contexthierarchy.config.ConfigOne;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextHierarchy({
        @ContextConfiguration(classes = Application.class),
        @ContextConfiguration(classes = ConfigOne.class),
        // wird jetzt auch zu WebMergedContextConfiguration -> immer bei reuse mit Vererbung arbeiten
})
public abstract class TestBase {
//    @TestConfiguration
//    static class TestConfig {
//
//    }
}
