package com.goeuro.bus.route.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 *
 */
@JsonSerialize
@Data
public class DirectRouteResponseDTO {

    @JsonProperty("dep_sid")
    private Integer departureSid;

    @JsonProperty("arr_sid")
    private Integer arrivalSid;

    @JsonProperty("direct_bus_route")
    private Boolean directBusRoute;
}
