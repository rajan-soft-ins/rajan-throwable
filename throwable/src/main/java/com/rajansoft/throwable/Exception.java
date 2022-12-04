package com.rajansoft.throwable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public interface Exception {

    String getMessage();

    String getError();

    String getPath();

    String getTraceId();

    Integer getCode();

    LocalDateTime getTimestamp();

    Exception getCause();

    default Map<String, Object> getParameters() {
        return Collections.emptyMap();
    }

    static ExceptionBuilder builder(String message) {
        return builder(message, UUID.randomUUID().toString());
    }

    static ExceptionBuilder builder(String message, String traceId) {
        return new ExceptionBuilder(message, traceId);
    }

}
