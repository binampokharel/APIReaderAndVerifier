package org.zipCodeTester;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    private boolean result;
    private Integer statusCode;
    private String locationId;
}
