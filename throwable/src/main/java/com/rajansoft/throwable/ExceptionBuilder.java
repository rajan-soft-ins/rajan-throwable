package com.rajansoft.throwable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ExceptionBuilder {

    private static final Set<String> RESERVED_PROPERTIES =
        new HashSet<>(Arrays.asList("message", "error", "path", "traceId", "code", "timestamp"));

    String message;
    String error;
    String path;
    String traceId;
    Integer code;
    LocalDateTime timestamp = LocalDateTime.now();
    Map<String, Object> parameters = new HashMap<>();

    public ExceptionBuilder(String message, String traceId) {
        if (Objects.isNull(message) || message.isEmpty()) {
            throw new IllegalArgumentException("message is required.");
        }
        if (Objects.isNull(traceId) || traceId.isEmpty()) {
            throw new IllegalArgumentException("traceId is required.");
        }
        this.traceId = traceId;
        this.message = message;
    }

    public ExceptionBuilder error(String error) {
        this.error = error;
        return this;
    }

    public ExceptionBuilder path(String path) {
        this.path = path;
        return this;
    }

    public ExceptionBuilder code(Integer code) {
        this.code = code;
        return this;
    }

    public ExceptionBuilder timestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ExceptionBuilder parameters(String key, Object value) {
        if (RESERVED_PROPERTIES.contains(key)) {
            throw new IllegalArgumentException("Property " + key + " is reserved");
        }
        parameters.put(key, value);
        return this;
    }

    public ThrowableException build() {
        return new DefaultException(this);
    }

}
