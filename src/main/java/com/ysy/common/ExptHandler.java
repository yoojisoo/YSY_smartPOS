package com.ysy.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExptHandler {

	
	
	
	@ExceptionHandler(value=Exception.class)
	public String handleException(Exception e){
		
		System.out.println("exception 호출 user = " + e.getMessage());
		return "Exception message : error";
	}
}
