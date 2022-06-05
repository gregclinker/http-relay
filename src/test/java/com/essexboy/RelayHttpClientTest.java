package com.essexboy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RelayHttpClientTest {

    @Test
    public void get() throws Exception {
        final HttpEndPoint httpEndPoint = new HttpEndPoint();
        httpEndPoint.setUrl("https://reqres.in/api/products/3");
        httpEndPoint.setMethod("GET");
        final String response = new RelayHttpClientImpl().execute(httpEndPoint);
        assertNotNull(response);
    }

    @Test
    public void post() throws Exception {
        final HttpEndPoint httpEndPoint = new HttpEndPoint();
        httpEndPoint.setUrl("https://reqres.in/api/products/3");
        httpEndPoint.setMethod("POST");
        String requestBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        final String responseBody = new RelayHttpClientImpl().execute(httpEndPoint, requestBody);
        assertNotNull(responseBody);
    }

    @Test
    public void error() throws Exception {
        final HttpEndPoint httpEndPoint = new HttpEndPoint();
        httpEndPoint.setUrl("https://bad-host");
        httpEndPoint.setMethod("GET");
        Assertions.assertThrows(UnknownHostException.class, () -> {
            new RelayHttpClientImpl().execute(httpEndPoint);
        });
    }
}