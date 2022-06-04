package com.essexboy;

import lombok.*;

/**
 * App config
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RelayConfig {
    private boolean verbose = true;
    private int repeat = 0;
    private int interval = 10;
    private int timeout = 5;
    private String namespace;
    private HttpEndPoint source;
    private HttpEndPoint sink;
}
