# CXF Samples

## Client and local Endpoint based on WSDL

- Based on hello_world.wsdl located in `src/main/resources`. 
- SOAP Service and classes are generated via Maven cxf-codegen-plugin (see `pom.xml`)
- `com.github.masooh.samples.JaxWsClientTest` uses the JAX-WS client (`client.xml`) to call the local JAX-WS server (`endpoint.xml`).
