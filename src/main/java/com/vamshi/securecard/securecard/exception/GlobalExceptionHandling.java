package com.vamshi.securecard.securecard.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandling {

	@ExceptionHandler(exception = NullPointerException.class)
	public ResponseEntity<Map<String, String>> handleNullPOinterException(NullPointerException ne) {
		Map<String, String> map = new HashMap<>();
		map.put("errorCode", "101");
		map.put("errorMessage", "There is some null pointer exception");
		map.put("details", ne.getMessage());
		return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(exception = NumberFormatException.class)
	public ResponseEntity<Map<String, String>> handleNumberFormatException(NumberFormatException nfe) {
		Map<String, String> map = new HashMap<>();
		map.put("errorCode", "102");
		map.put("errorMessage", "do not give inputs as 0");
		map.put("details", nfe.getMessage());
		return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RestControllerAdvice
	public class ValidationExceptionHandler {

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage())
	        );
	        return ResponseEntity.badRequest().body(Map.of("errors", errors));
	    }
	}



}
