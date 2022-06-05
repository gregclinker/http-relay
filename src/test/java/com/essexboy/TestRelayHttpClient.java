package com.essexboy;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fake http client for testing.
 * Allows a response body to be set.
 */
@Setter
public class TestRelayHttpClient implements RelayHttpClient {

    final static Logger LOGGER = LoggerFactory.getLogger(TestRelayHttpClient.class);

    private String returnBody;

    @Override
    public String execute(HttpEndPoint httpEndPoint, String body) {
        LOGGER.debug("execute {}, body {}", httpEndPoint, body);
        return returnBody;
    }

    @Override
    public String execute(HttpEndPoint httpEndPoint) throws Exception {
        return execute(httpEndPoint, null);
    }
}
