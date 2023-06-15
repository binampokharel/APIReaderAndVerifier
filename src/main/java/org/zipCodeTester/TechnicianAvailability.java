package org.zipCodeTester;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnicianAvailability {
    @JsonProperty("TechnicianId")
    private String TechnicianId;
    @JsonProperty("Distance")
    private double Distance;
    @JsonProperty("Time")
    private double Time;
    @JsonProperty("TechnicianName")
    private String TechnicianName;
}