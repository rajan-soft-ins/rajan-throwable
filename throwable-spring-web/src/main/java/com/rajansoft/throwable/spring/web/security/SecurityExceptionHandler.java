package com.rajansoft.throwable.spring.web.security;

import com.rajansoft.throwable.Exception;
import com.rajansoft.throwable.spring.web.AdviceTrait;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public interface SecurityExceptionHandler extends AdviceTrait {

    @ExceptionHandler({
        AccessDeniedException.class,
        CsrfException.class,
        InvalidCsrfTokenException.class,
        MissingCsrfTokenException.class,
        AccountExpiredException.class,
        CredentialsExpiredException.class,
        DisabledException.class,
        LockedException.class,
        SessionAuthenticationException.class,
        InvalidCookieException.class
    })
    default ResponseEntity<Exception> handleException(Throwable ex, WebRequest request) {
        return create(ex, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    default ResponseEntity<Exception> handleException(UsernameNotFoundException ex, WebRequest request) {
        return create(ex, HttpStatus.NOT_FOUND, request);
    }

    @Override
    default String generatePath(WebRequest request) {
        return request.getDescription(true);
    }

}
