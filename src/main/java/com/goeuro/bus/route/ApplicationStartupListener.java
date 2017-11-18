package com.goeuro.bus.route;

import com.goeuro.bus.route.data.loader.BusRoutesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartupListener.class);

    @Value("${routes.file}")
    private String routesFile;

    @Autowired
    private BusRoutesLoader busRoutesLoader;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        LOGGER.info("Started process of routs loading process from data file: {}", routesFile);

        busRoutesLoader.initRoutesFromInput(routesFile);

        LOGGER.info("Finished process of routs loading process from data file: {}", routesFile);
    }
}