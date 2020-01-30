package com.app.controlleradvice;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.app.exception.AppException;
import com.app.exception.ErrorSchedulingTaskException;
import com.mongodb.MongoException;

@ControllerAdvice(basePackages = "com.app")
public class AppControllerAdvice {

	@ExceptionHandler(MongoException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "DB ERROR YO ADMIN!")
	public void mongoException(MongoException exception) {
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> anyOtherException(HttpServletRequest request, Throwable ex) {
		if(ex instanceof MongoException) {
			return new ResponseEntity<>(
					new AppException(
							LocalDateTime.now().toString(), "error", "db error contact admin",
							request.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
		} else if(ex instanceof ErrorSchedulingTaskException) {
			return new ResponseEntity<>(
					new AppException(
							LocalDateTime.now().toString(), "error", "error scheduling task",
							request.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(
				new AppException(
						LocalDateTime.now().toString(), "error", "bad request check docs",
						request.getRequestURI()), HttpStatus.BAD_REQUEST);
	}
}
