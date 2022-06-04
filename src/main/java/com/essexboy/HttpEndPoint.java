package com.essexboy;

import lombok.*;

import java.util.List;

/**
 * POJO for source & sink
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpEndPoint {
    private String name;
    private String url;
    private String method;
    private List<RelayHeader> headers;
}
