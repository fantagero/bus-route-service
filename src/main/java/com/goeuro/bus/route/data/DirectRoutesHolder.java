package com.goeuro.bus.route.data;

import com.goeuro.bus.route.data.entity.StationsPair;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope("singleton")
public class DirectRoutesHolder {

    private final Set<StationsPair> directRoutes = new HashSet<>();

    public Boolean directRouteExists(Integer departureSid, Integer arrivalSid) {
        return directRoutes.contains(new StationsPair(departureSid, arrivalSid));
    }

    public void addDirectRoute(StationsPair stationPair) {
        if (!directRoutes.contains(stationPair)) {
            directRoutes.add(stationPair);
        }
    }
}
