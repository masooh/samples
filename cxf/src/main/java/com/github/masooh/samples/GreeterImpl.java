package com.github.masooh.samples;

import org.apache.hello_world_soap_http.BadRecordLitFault;
import org.apache.hello_world_soap_http.Greeter;
import org.apache.hello_world_soap_http.NoSuchCodeLitFault;

public class GreeterImpl implements Greeter {
    public String testNillable(String nillElem, int intElem) {
        return null;
    }

    public void testDocLitFault(String faultType) throws BadRecordLitFault, NoSuchCodeLitFault {

    }

    public String greetMe(String requestType) {
        return null;
    }

    public void greetMeOneWay(String requestType) {

    }

    public String sayHi() {
        return "Hi from Greeter";
    }

    public String greetMeLater(long requestType) {
        return null;
    }

    public String greetMeSometime(String requestType) {
        return null;
    }
}
