package com.tcs.securecard.securecard.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandling {
	
	
	@ExceptionHandler(exception = NullPointerException.class)
	public Map<String, String> handleNpe(NullPointerException ne){
		Map<String, String> hm = new HashMap<>();
		hm.put("errorCode", "101");
		hm.put("errorMessage", "There is some null pointer Exception");
		hm.put("details", ne.getMessage());
		return hm;
	}
	
	@ExceptionHandler(exception = ArithmeticException.class)
	public Map<String, String> handleAe(ArithmeticException ae){
		Map<String, String> hm = new HashMap<>();
		hm.put("errorCode", "102");
		hm.put("errorMessage", "There is some Arithmetic Exception");
		hm.put("details", ae.getMessage());
		return hm;
	}
	
	@ExceptionHandler(exception=MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleBeanError(MethodArgumentNotValidException me){
		Map<String, String> hm = new HashMap<>();
		BindingResult br = me.getBindingResult();
		br.getFieldErrors().stream().forEach(x -> hm.put(x.getField(), x.getDefaultMessage()));
		return new ResponseEntity<>(hm, HttpStatus.OK);
	}
	
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<Map<String, String>> handleConstraintViolation(
//	        ConstraintViolationException ex) {
//
//	    Map<String, String> errors = new HashMap<>();
//
//	    ex.getConstraintViolations().forEach(v -> {
//	        String field = v.getPropertyPath().toString();
//	        errors.put(field, v.getMessage());
//	    });
//
//	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//	}

	
}
