package com.goeuro.bus.route.exception;

public interface ErrorMessageKeys {

    interface DirectRoute {
        String DEPARTURE_SID_NOT_PROVIDED = "departure.sid.not.provided";
        String ARRIVAL_SID_NOT_PROVIDED = "arrival.sid.not.provided";
        String DEPARTURE_SID_MIN_VALUE_SHOULD_NOT_BE_LESS = "departure.sid.min.value.should.not.be.less";
        String ARRIVAL_SID_MIN_VALUE_SHOULD_NOT_BE_LESS = "arrival.sid.min.value.should.not.be.less";
        String ROUTES_FILE_PATH_NOT_PROVIDED = "routes.file.path.not.provided";
        String FAILED_LOAD_ROUTES_FROM_DATA_FILE = "failed.load.routes.from.data.file";
    }
}
