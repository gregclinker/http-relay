package com.essexboy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RelayHttpClientImplTest {

    @Test
    public void test1() throws Exception {
        final HttpEndPoint httpEndPoint = new HttpEndPoint();
        httpEndPoint.setUrl("https://reqres.in/api/products/3");
        httpEndPoint.setMethod("GET");
        final String body = new RelayHttpClientImpl().execute(httpEndPoint);
        assertNotNull(body);
    }
}