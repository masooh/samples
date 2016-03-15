package com.github.masooh.samples;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.hello_world_soap_http.Greeter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/client.xml", "/endpoint.xml"})
public class JaxWsClientTest {

    @Inject
    Greeter greeter;

    @Test
    public void simpleCall() {
        final String hi = greeter.sayHi();
        assertEquals(hi, "Hi from Greeter");
    }
}
