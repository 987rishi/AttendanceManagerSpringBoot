package com.rishi.exceptions;

import java.util.NoSuchElementException;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
//GLOBAL EXCEPTION HANDLER CLASS
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
@ControllerAdvice
public class GlobalExceptionHandlerClass {
	@ExceptionHandler(exception = NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchEleExceptions(NoSuchElementException ex,WebRequest request){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(exception = IllegalArgumentException.class)
	public ResponseEntity<String> illegalArgumentsException(IllegalAccessException ex,WebRequest request){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception =DataAccessResourceFailureException.class)
	public ResponseEntity<String> databaseException(DataAccessResourceFailureException ex,WebRequest request){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(exception =UsernameNotFoundException.class)
	public ResponseEntity<String> authException(UsernameNotFoundException ex,WebRequest request){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(exception =IllegalAccessException.class)
	public ResponseEntity<String> accessException(IllegalAccessException ex,WebRequest request){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(exception = DataIntegrityViolationException.class)
	public ResponseEntity<String> psqlException(DataIntegrityViolationException ex,WebRequest request){
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
	

}
