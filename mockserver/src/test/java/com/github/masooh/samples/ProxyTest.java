package com.github.masooh.samples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.verify.VerificationTimes.exactly;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.proxy.ProxyClient;
import org.mockserver.junit.ProxyRule;

public class ProxyTest {

    @Rule
    public ProxyRule mockServerRule = new ProxyRule(this);

    private ProxyClient proxyClient;

    private int port;

    private HttpClient client;

    @Before
    public void resetServer() throws Exception {
        port = mockServerRule.getHttpPort();

        proxyClient.reset();

        HttpHost httpHost = new HttpHost("localhost", port);
        DefaultProxyRoutePlanner defaultProxyRoutePlanner = new DefaultProxyRoutePlanner(httpHost);
        client = HttpClients.custom().setRoutePlanner(defaultProxyRoutePlanner).build();
    }

    @Test
    public void testDumpToLogAsJava() throws Exception {
        final HttpGet httpGet = new HttpGet("http://mock-server.com/proxy/getting_started.html");
        org.apache.http.HttpResponse response = client.execute(httpGet);

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertTrue(response.getEntity().getContentLength() > 0);

        proxyClient.verify(
                request()
                        .withPath("/proxy/.*"),
                exactly(1)
        );

        proxyClient.dumpToLogAsJava();
    }
}
