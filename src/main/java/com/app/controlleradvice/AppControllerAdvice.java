package com.app.controlleradvice;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.exception.AppExceptionMessage;
import com.app.exception.ErrorSchedulingTaskException;
import com.app.exception.InvalidConfirmationCode;
import com.app.exception.UnAuthorizedException;
import com.app.exception.UnConfirmedRegistrationExcpetion;
import com.app.exception.UserAlreadyPresentException;
import com.mongodb.MongoException;

@ControllerAdvice(basePackages = "com.app")
public class AppControllerAdvice {
    
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> anyOtherException(HttpServletRequest request, Throwable ex) {
        if(ex instanceof MongoException) {
            return new ResponseEntity<>(
                    new AppExceptionMessage(
                            LocalDateTime.now().toString(), "error", "Db error contact admin.",
                            request.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else if(ex instanceof ErrorSchedulingTaskException) {
            return new ResponseEntity<>(
                    new AppExceptionMessage(
                            LocalDateTime.now().toString(), "error", "Error scheduling task.",
                            request.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
        } else if(ex instanceof UnAuthorizedException) {
            return new ResponseEntity<>(
                    new AppExceptionMessage(
                            LocalDateTime.now().toString(), "error", "Invalid email or password.",
                            request.getRequestURI()), HttpStatus.UNAUTHORIZED);
        } else if(ex instanceof UnConfirmedRegistrationExcpetion) {
            return new ResponseEntity<>(
                    new AppExceptionMessage(
                            LocalDateTime.now().toString(), "error", "Unconfirmed registration.",
                            request.getRequestURI()), HttpStatus.NOT_FOUND);
        } else if(ex instanceof InvalidConfirmationCode) {
            return new ResponseEntity<>(
                    new AppExceptionMessage(
                            LocalDateTime.now().toString(), "error", "Invalid confirmation code.",
                            request.getRequestURI()), HttpStatus.NOT_FOUND);
        } else if(ex instanceof UserAlreadyPresentException) {
            return new ResponseEntity<>(
                    new AppExceptionMessage(
                            LocalDateTime.now().toString(), "error", "User already present.",
                            request.getRequestURI()), HttpStatus.NOT_FOUND);
        }
        logger.error("ERROR: {}", ex);
        return new ResponseEntity<>(
                new AppExceptionMessage(
                        LocalDateTime.now().toString(), "error", "Bad request check docs.",
                        request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }
}
