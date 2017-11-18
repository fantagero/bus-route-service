package com.goeuro.bus.route.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StationsPair {

    private Integer departureSid;

    private Integer arrivalSid;

}
