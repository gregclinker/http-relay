package com.essexboy;

import lombok.*;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RelayHeader implements Header {
    private String name;
    private String value;

    @Override
    public HeaderElement[] getElements() throws ParseException {
        return new HeaderElement[0];
    }
}
