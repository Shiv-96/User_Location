package com.location.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


/**
 * represents to Handle the Exception
 * 
 * This class is converting the normal exception into response entity that will have more flexibility
 * 
 * @author SHIV KUMAR
 *
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<MyErrorDetails> userExceptionHandler(UserException ue, WebRequest we){
		
		MyErrorDetails error = new MyErrorDetails();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ue.getMessage());
		error.setDescription(we.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> otherExceptionHandler(Exception ex, WebRequest we){
		
		MyErrorDetails error = new MyErrorDetails();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(ex.getMessage());
		error.setDescription(we.getDescription(false));
		
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me){
		
		MyErrorDetails error = new MyErrorDetails();
		
		error.setTimestamp(LocalDateTime.now());
		error.setMessage(me.getMessage());
		error.setDescription(me.getBindingResult().getFieldError().getDefaultMessage());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	
	
	

}
