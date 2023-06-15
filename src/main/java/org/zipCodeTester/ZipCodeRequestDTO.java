package org.zipCodeTester;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZipCodeRequestDTO {
    private String request_type;
    private String zipCode;
}
