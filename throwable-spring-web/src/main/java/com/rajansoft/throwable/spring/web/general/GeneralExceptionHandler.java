package com.rajansoft.throwable.spring.web.general;

import com.rajansoft.throwable.Exception;
import com.rajansoft.throwable.ThrowableException;
import com.rajansoft.throwable.spring.web.AdviceTrait;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

public interface GeneralExceptionHandler extends AdviceTrait {

    @ExceptionHandler({ThrowableException.class})
    default ResponseEntity<Exception> handleException(ThrowableException ex, WebRequest request) {
        return create(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({Throwable.class})
    default ResponseEntity<Exception> handleException(Throwable throwable, WebRequest request) {
        return create(throwable, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({ResponseStatusException.class})
    default ResponseEntity<Exception> handleException(ResponseStatusException ex, WebRequest request) {
        return create(ex, ex.getStatus(), request);
    }

    @ExceptionHandler({UnsupportedOperationException.class})
    default ResponseEntity<Exception> handleException(UnsupportedOperationException ex, WebRequest request) {
        return create(ex, HttpStatus.NOT_IMPLEMENTED, request);
    }

}
