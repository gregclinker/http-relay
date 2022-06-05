package com.essexboy;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Class to manage the transformation between the source results and the data to be sent to the source
 */
public class SourceToSinkTransformer {
    public List<String> transform(String sink) throws IOException {
        return Arrays.asList(Arrays.stream(sink.split("\n"))
                .filter(s -> !s.matches("^#.*"))
                .map(SourceToSinkTransformer::splitNameValue)
                .toArray(String[]::new));
    }

    private static String splitNameValue(String string) {
        // split on anything followed by a space, followed by anything that's not a space
        final String[] split = string.split("\\s+(?=\\S+$)");
        return "name=" + split[0] + ", value=" + split[1];
    }
}
