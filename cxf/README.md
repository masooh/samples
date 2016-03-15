# CXF Samples

## Client and local Endpoint based on WSDL

- Based on [hello_world.wsdl](src/main/resources/hello_world.wsdl) located in `src/main/resources`. 
- SOAP Service and classes are generated via Maven cxf-codegen-plugin (see [pom.xml](pom.xml))
- [JaxWsClientTest](src/test/java/com/github/masooh/samples/JaxWsClientTest.java) uses the JAX-WS client ([client.xml](src/main/resources/client.xml)) to call the local JAX-WS server ([endpoint.xml](src/main/resources/endpoint.xml)).
