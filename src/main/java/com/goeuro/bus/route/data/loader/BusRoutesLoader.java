package com.goeuro.bus.route.data.loader;

import com.goeuro.bus.route.data.builder.StationsPairBuilder;
import com.goeuro.bus.route.data.DirectRoutesHolder;
import com.goeuro.bus.route.data.entity.StationsPair;
import com.goeuro.bus.route.exception.ApplicationInitException;
import com.goeuro.bus.route.exception.ErrorMessageKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

@Component
public class BusRoutesLoader {

    @Autowired
    private ApplicationContext applicationContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(BusRoutesLoader.class);

    private static final String REGEXP_LINE_DELIMITER = "\r?\n|\r";

    private static final String REGEXP_ITEM_DELIMITER = "\\s";

    @Autowired
    private DirectRoutesHolder directRoutesProvider;

    public void initRoutesFromInput(final String routesFile) {
        LOGGER.info("Started routs loading from data file");

        if(routesFile == null || routesFile.isEmpty()) {
            LOGGER.error("Required routes file path argument not provided");
            throw new ApplicationInitException(ErrorMessageKeys.DirectRoute.ROUTES_FILE_PATH_NOT_PROVIDED);
        }

        Resource resource = applicationContext.getResource("file:" + routesFile);
        DirectRoutesHolder busRoutes = new DirectRoutesHolder();

        try {
            Scanner scanner = new Scanner(resource.getInputStream()).useDelimiter(REGEXP_LINE_DELIMITER);
            //skip first line with routs count
            scanner.nextLine();
            String next = scanner.nextLine();

            while (scanner.hasNext()) {
                Integer[] routeIdWithStationIds = stream(next.split(REGEXP_ITEM_DELIMITER)).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
                Integer routeId = routeIdWithStationIds[0];
                Integer[] stationIds = stream(routeIdWithStationIds).skip(1).toArray(Integer[]::new);
            stream(stationIds)
                    .flatMap(nextStationId -> processPairs(nextStationId, stationIds))
                    .forEach(pair -> directRoutesProvider.addDirectRoute(pair));

                next = scanner.nextLine();
            }
        } catch (IOException io) {
            LOGGER.error("Exception during routs loading from data file", io);
            throw new ApplicationInitException(ErrorMessageKeys.DirectRoute.FAILED_LOAD_ROUTES_FROM_DATA_FILE);
        }

        LOGGER.info("Finished routs loading from data file");
    }

    private static Stream<StationsPair> processPairs(Integer stationId, Integer[] stationIds) {
        return stream(stationIds)
                .filter(currentStationId -> !currentStationId.equals(stationId))
                .map(currentStationId -> StationsPairBuilder.getBuilder().departureSid(currentStationId).arrivalSid(stationId).build());
    }
}
