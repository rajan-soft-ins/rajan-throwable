package com.rajansoft.throwable.spring.web;

import com.rajansoft.throwable.Exception;
import com.rajansoft.throwable.ExceptionBuilder;
import com.rajansoft.throwable.ThrowableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.util.WebUtils.ERROR_EXCEPTION_ATTRIBUTE;

public interface AdviceTrait {

    default ResponseEntity<Exception> create(Throwable throwable, HttpStatus status, WebRequest request) {
        return create(throwable, status, new HttpHeaders(), request);
    }

    default ResponseEntity<Exception> create(Throwable throwable,
                                             HttpStatus status,
                                             HttpHeaders headers,
                                             WebRequest request) {
        Exception body = toException(throwable, request);
        return create(throwable, body, status, headers, request);
    }

    default ResponseEntity<Exception> create(Throwable throwable, Exception body, HttpStatus status, WebRequest request) {
        return create(throwable, body, status, new HttpHeaders(), request);
    }

    default ResponseEntity<Exception> create(Throwable throwable,
                                             Exception body,
                                             HttpStatus status,
                                             HttpHeaders headers,
                                             WebRequest request) {
        if (Objects.equals(status, HttpStatus.INTERNAL_SERVER_ERROR)) {
            request.setAttribute(ERROR_EXCEPTION_ATTRIBUTE, throwable, SCOPE_REQUEST);
        }

        return ResponseEntity
            .status(status)
            .headers(headers)
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(body);
    }

    default ThrowableException toException(Throwable throwable, WebRequest request) {
        return toException(throwable, UUID.randomUUID().toString(), request);
    }

    default ThrowableException toException(Throwable throwable, String traceId, WebRequest request) {
        return toException(throwable, traceId, null, null, request);
    }

    default ThrowableException toException(Throwable throwable, Integer code, WebRequest request) {
        return toException(throwable, UUID.randomUUID().toString(), code, request);
    }

    default ThrowableException toException(Throwable throwable, String traceId, Integer code, WebRequest request) {
        return toException(throwable, traceId, code, null, request);
    }

    default ThrowableException toException(Throwable throwable, Map<String, Object> parameters, WebRequest request) {
        return toException(throwable, UUID.randomUUID().toString(), parameters, request);
    }

    default ThrowableException toException(Throwable throwable,
                                           String traceId,
                                           Map<String, Object> parameters,
                                           WebRequest request) {
        return toException(throwable, traceId, null, parameters, request);
    }

    default ThrowableException toException(Throwable throwable,
                                           Integer code,
                                           Map<String, Object> parameters,
                                           WebRequest request) {
        return toException(throwable, UUID.randomUUID().toString(), code, parameters, request);
    }

    default ThrowableException toException(Throwable throwable,
                                           String traceId,
                                           @Nullable Integer code,
                                           @Nullable Map<String, Object> parameters,
                                           WebRequest request) {
        ThrowableException exception = builder(throwable, traceId, code, parameters, request).build();
        exception.setStackTrace(createStackTrace(throwable));
        return exception;
    }

    private ExceptionBuilder builder(Throwable throwable,
                                     String traceId,
                                     @Nullable Integer code,
                                     @Nullable Map<String, Object> parameters,
                                     WebRequest request) {
        ExceptionBuilder builder = Exception.builder(throwable.getMessage(), traceId)
            .error(throwable.getClass().getSimpleName())
            .path(generatePath(request))
            .code(code);

        if (Objects.nonNull(parameters) && !parameters.isEmpty()) {
            parameters.forEach(builder::parameters);
        }

        return builder;
    }

    default String generatePath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private StackTraceElement[] createStackTrace(Throwable throwable) {
        final Throwable cause = throwable.getCause();

        if (Objects.isNull(cause)) {
            return throwable.getStackTrace();
        } else {
            StackTraceElement[] next = cause.getStackTrace();
            StackTraceElement[] current = throwable.getStackTrace();

            int length = current.length - lengthOfTrailingPartialSubList(asList(next), asList(current));
            StackTraceElement[] stackTrace = new StackTraceElement[length];
            System.arraycopy(current, 0, stackTrace, 0, length);
            return stackTrace;
        }
    }

    private int lengthOfTrailingPartialSubList(List<StackTraceElement> source, List<StackTraceElement> target) {
        final int sourceSize = source.size() - 1;
        final int targetSize = target.size() - 1;
        int count = 0;

        while (count <= sourceSize && count <= targetSize &&
            Objects.equals(source.get(sourceSize - count), target.get(targetSize - count))) {
            count++;
        }

        return count;
    }

}
