package com.rajansoft.throwable.spring.web.http;

import com.rajansoft.throwable.Exception;
import com.rajansoft.throwable.spring.web.AdviceTrait;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public interface HttpExceptionHandler extends AdviceTrait {

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    default ResponseEntity<Exception> handleException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!CollectionUtils.isEmpty(supportedMethods)) {
            headers.setAllow(supportedMethods);
        }
        return create(ex, HttpStatus.METHOD_NOT_ALLOWED, headers, request);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    default ResponseEntity<Exception> handleException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            headers.setAccept(mediaTypes);
            if (request instanceof ServletWebRequest servletWebRequest &&
                Objects.equals(HttpMethod.PATCH, servletWebRequest.getHttpMethod())) {
                headers.setAcceptPatch(mediaTypes);
            }
        }
        return create(ex, HttpStatus.UNSUPPORTED_MEDIA_TYPE, headers, request);
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    default ResponseEntity<Exception> handleException(HttpMediaTypeNotAcceptableException ex, WebRequest request) {
        return create(ex, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler({HttpServerErrorException.class})
    default ResponseEntity<Exception> handleException(HttpServerErrorException ex, WebRequest request) {
        return create(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
