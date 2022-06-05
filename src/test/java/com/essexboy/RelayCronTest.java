package com.essexboy;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RelayCronTest {

    @Test
    public void test1() throws Exception {
        // use a fake http client & set the response body to test data
        final TestRelayHttpClient relayHttpClient = new TestRelayHttpClient();
        relayHttpClient.setReturnBody(IOUtils.toString(getClass().getResourceAsStream("/jmx-mertics-small.test"), Charset.defaultCharset()));

        final InputStream inputStream = getClass().getResourceAsStream("/testConfig.yaml");
        assertNotNull(inputStream);

        final RelayCron relayCron = new RelayCron(inputStream, relayHttpClient);
        assertNotNull(relayCron.getRelayConfig());

        relayCron.cron();
        Thread.sleep(2500l);
        relayCron.stop();
        assertEquals(3, relayCron.getCount());
    }
}