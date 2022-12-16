package com.rajansoft.throwable.spring.web.io;

import com.rajansoft.throwable.Exception;
import com.rajansoft.throwable.spring.web.AdviceTrait;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

public interface IOExceptionHandler extends AdviceTrait {

    @ExceptionHandler({MissingPathVariableException.class})
    default ResponseEntity<Exception> handleException(MissingPathVariableException ex, WebRequest request) {
        return create(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    default ResponseEntity<Exception> handleException(MissingServletRequestParameterException ex, WebRequest request) {
        return create(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ServletRequestBindingException.class})
    default ResponseEntity<Exception> handleException(ServletRequestBindingException ex, WebRequest request) {
        return create(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ConversionNotSupportedException.class})
    default ResponseEntity<Exception> handleException(ConversionNotSupportedException ex, WebRequest request) {
        return create(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    default ResponseEntity<Exception> handleException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return create(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    default ResponseEntity<Exception> handleException(HttpMessageNotReadableException ex, WebRequest request) {
        return create(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({HttpMessageNotWritableException.class})
    default ResponseEntity<Exception> handleException(HttpMessageNotWritableException ex, WebRequest request) {
        return create(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    default ResponseEntity<Exception> handleException(MissingServletRequestPartException ex, WebRequest request) {
        return create(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({BindException.class})
    default ResponseEntity<Exception> handleException(BindException ex, WebRequest request) {
        return create(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    default ResponseEntity<Exception> handleException(NoHandlerFoundException ex, WebRequest request) {
        return create(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({AsyncRequestTimeoutException.class})
    default ResponseEntity<Exception> handleException(AsyncRequestTimeoutException ex, WebRequest request) {
        return create(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({HttpMediaTypeException.class})
    default ResponseEntity<Exception> handleException(HttpMediaTypeException ex, WebRequest request) {
        return create(ex, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler({MultipartException.class})
    default ResponseEntity<Exception> handleException(MultipartException ex, WebRequest request) {
        return create(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    default ResponseEntity<Exception> handleException(MaxUploadSizeExceededException ex, WebRequest request) {
        return create(ex, HttpStatus.BAD_REQUEST, request);
    }

}
