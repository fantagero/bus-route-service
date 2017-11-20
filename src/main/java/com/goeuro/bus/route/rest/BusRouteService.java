package com.goeuro.bus.route.rest;

import com.goeuro.bus.route.data.DirectRoutesHolder;
import com.goeuro.bus.route.dto.BusRouteServiceURLs;
import com.goeuro.bus.route.dto.DirectRouteResponseDTO;
import com.goeuro.bus.route.dto.builder.DirectRouteResponseDTOBuilder;
import com.goeuro.bus.route.exception.ErrorMessageKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class BusRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusRouteService.class);

    @Autowired
    private DirectRoutesHolder directRoutesProvider;

    @RequestMapping(value = BusRouteServiceURLs.GET_DIRECT_ROUTE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<DirectRouteResponseDTO> getDirectRoute(
            @NotNull(message = ErrorMessageKeys.DirectRoute.DEPARTURE_SID_NOT_PROVIDED)
            @Min(value = 0, message = ErrorMessageKeys.DirectRoute.DEPARTURE_SID_MIN_VALUE_SHOULD_NOT_BE_LESS)
            @RequestParam(value = "dep_sid") Integer departureSid,

            @NotNull(message = ErrorMessageKeys.DirectRoute.ARRIVAL_SID_NOT_PROVIDED)
            @Min(value = 0, message = ErrorMessageKeys.DirectRoute.ARRIVAL_SID_MIN_VALUE_SHOULD_NOT_BE_LESS)
            @RequestParam(value = "arr_sid") Integer arrivalSid) {
        LOGGER.debug("Direct route GET request has been received for departure sid = {} and arrival sid = {}", departureSid, arrivalSid);

        DirectRouteResponseDTO directRouteResponse = DirectRouteResponseDTOBuilder.getBuilder()
                .departureSid(departureSid)
                .arrivalSid(arrivalSid)
                .directBusRoute(directRoutesProvider.directRouteExists(departureSid, arrivalSid))
                .build();

        LOGGER.debug("Returning response for Direct Route GET request for departure sid = {} and arrival sid = {} : {}", directRouteResponse);
        return new ResponseEntity<>(directRouteResponse, HttpStatus.OK);
    }
}
