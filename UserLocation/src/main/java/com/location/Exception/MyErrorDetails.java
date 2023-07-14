package com.location.Exception;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * Represent the MyErrorDetails to give details of the exception
 * 
 * @author SHIV KUMAR
 *
 */

@Data
public class MyErrorDetails {
	
	private LocalDateTime timestamp;
	
	private String message;
	
	private String description;
	
}
