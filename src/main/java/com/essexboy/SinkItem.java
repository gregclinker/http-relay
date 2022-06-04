package com.essexboy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO to manage sink request body
 */
@Getter
@Setter
@ToString
public class SinkItem {
    private String namespace;
    private String value;
}
