package com.goeuro.bus.route.dto.builder;

import com.goeuro.bus.route.dto.DirectRouteResponseDTO;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Setter
public class DirectRouteResponseDTOBuilder {

    private Integer departureSid;

    private Integer arrivalSid;

    private Boolean directBusRoute;

    private DirectRouteResponseDTOBuilder() {
    }

    public static DirectRouteResponseDTOBuilder getBuilder() {
        return new DirectRouteResponseDTOBuilder();
    }

    public DirectRouteResponseDTO build() {
        DirectRouteResponseDTO result = new DirectRouteResponseDTO();
        result.setDepartureSid(this.departureSid);
        result.setArrivalSid(this.arrivalSid);
        result.setDirectBusRoute(this.directBusRoute);
        return result;
    }

}
