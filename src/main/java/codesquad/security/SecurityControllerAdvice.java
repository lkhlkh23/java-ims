package codesquad.security;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.UnsupportedFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice(annotations = Controller.class)
public class SecurityControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(SecurityControllerAdvice.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void emptyResultData() {
        log.debug("EntityNotFoundException is happened!");
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String unAuthorized(UnAuthorizedException e) {
        log.debug(e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String unAuthentication(UnAuthenticationException e) {
        log.debug(e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void ConstraintViolation(ConstraintViolationException e) {
        log.debug(e.getMessage());
    }

    @ExceptionHandler(UnsupportedFormatException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String UnsupportedLabelFormatException(UnsupportedFormatException e) {
        log.debug(e.getMessage());
        return "redirect:/labels/form";
    }
}
