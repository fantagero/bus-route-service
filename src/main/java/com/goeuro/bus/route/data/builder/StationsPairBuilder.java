package com.goeuro.bus.route.data.builder;

import com.goeuro.bus.route.data.entity.StationsPair;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Setter
public class StationsPairBuilder {

    private Integer departureSid;

    private Integer arrivalSid;

    private StationsPairBuilder() {
    }

    public static StationsPairBuilder getBuilder() {
        return new StationsPairBuilder();
    }

    public StationsPair build() {
        return new StationsPair(this.departureSid, this.arrivalSid);
    }
}
