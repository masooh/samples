package com.github.masooh.samples;

import static org.junit.Assert.assertEquals;
import static org.mockserver.model.HttpCallback.callback;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpStatusCode.CONFLICT_409;
import static org.mockserver.model.HttpStatusCode.CREATED_201;
import static org.mockserver.model.HttpStatusCode.OK_200;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.MockServerRule;
import org.mockserver.mock.action.ExpectationCallback;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class CallbackTest {

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this);

    private MockServerClient mockServerClient;

    private int port;
    private String baseUri;

    private final CloseableHttpClient client = HttpClients.createMinimal();

    @Before
    public void resetServer() throws Exception {
        port = ((ClientAndServer) mockServerClient).getPort();
        baseUri = "http://localhost:" + port;

        mockServerClient.reset();
    }

    /**
     * Important: Callback class is created for every request, so state must be static or external
     * @throws Exception
     */
    @Test
    public void testStatefulCallback() throws Exception {
        mockServerClient
                .when(
                        request()
                )
                .callback(
                        callback()
                                .withCallbackClass(StatefulListCallback.class.getName())
                );

        CloseableHttpResponse response = client.execute(new HttpGet(baseUri + "/anything"));

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("1", EntityUtils.toString(response.getEntity()));

        response = client.execute(new HttpPost(baseUri + "/anything"));

        assertEquals(201, response.getStatusLine().getStatusCode());
        assertEquals("", EntityUtils.toString(response.getEntity()));

        response = client.execute(new HttpGet(baseUri + "/anything"));

        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("2", EntityUtils.toString(response.getEntity()));

        response = client.execute(new HttpPost(baseUri + "/anything"));

        assertEquals(409, response.getStatusLine().getStatusCode());
        assertEquals("", EntityUtils.toString(response.getEntity()));
    }

    /**
     *  Simulate Stateful Behaviour like WireMock
     *  @see <a href="http://wiremock.org/stateful-behaviour.html">WireMock - Stateful Behaviour</a>
     */
    public static class StateMaschineCallback implements ExpectationCallback {
        public static HttpResponse created = response().withStatusCode(CREATED_201.code());
        public static HttpResponse list1 = response().withStatusCode(OK_200.code()).withBody("1");
        public static HttpResponse list2 = response().withStatusCode(OK_200.code()).withBody("2");
        public static HttpResponse conflict = response().withStatusCode(CONFLICT_409.code());

        private static State state = State.ONE;

        public enum State {
            ONE(list1) {
                @Override
                State post() {
                    return TWO;
                }
            },
            TWO(list2) {
                @Override
                State post() {
                    return this;
                }
            };

            State(HttpResponse response) {
                this.response = response;
            }

            HttpResponse response;

            abstract State post();
        }

        public HttpResponse handle(HttpRequest httpRequest) {
            if (httpRequest.getMethod().getValue().equalsIgnoreCase("post")) {
                if (state == State.TWO) {
                    return conflict;
                }
                state = state.post();
                return created;
            }
            return state.response;
        }
    }

    public static class StatefulListCallback implements ExpectationCallback {
        private static List<String> list = new ArrayList<String>();

        static {
            list.add("1");
        }

        public static HttpResponse created = response().withStatusCode(CREATED_201.code());
        public static HttpResponse conflict = response().withStatusCode(CONFLICT_409.code());

        public HttpResponse handle(HttpRequest httpRequest) {
            if (httpRequest.getMethod().getValue().equalsIgnoreCase("post")) {
                if (list.size() < 2) {
                    list.add(httpRequest.getBodyAsString());
                    return created;
                }
                return conflict;
            }
            return response().withStatusCode(OK_200.code()).withBody(String.valueOf(list.size()));
        }
    }
}
