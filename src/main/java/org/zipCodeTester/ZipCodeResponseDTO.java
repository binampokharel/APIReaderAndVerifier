package org.zipCodeTester;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class ZipCodeResponseDTO {
    @JsonProperty("InServiceArea")
    private String InServiceArea;
    @JsonProperty("ZipCode")
    private String ZipCode;
    @JsonProperty("LocationId")
    private String LocationId;
    @JsonProperty("TechnicianAvailability")
    private List<TechnicianAvailability> TechnicianAvailability;

}

