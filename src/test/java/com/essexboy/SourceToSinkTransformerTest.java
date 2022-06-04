package com.essexboy;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceToSinkTransformerTest {

    @Test
    void transform() throws IOException {
        final String sourceBody = IOUtils.toString(getClass().getResourceAsStream("/jmx-mertics-full.test"), Charset.defaultCharset());
        final List<String> transforms = new SourceToSinkTransformer().transform(sourceBody);
        assertEquals(210, transforms.size());
        assertEquals("name=jmx_exporter_build_info{version=\"0.16.1\",name=\"Prometheus JMX Exporter - Http Server\",}, value=1.0", transforms.get(0));
    }
}