package com.ysy.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExptHandler {

	
	
	
	@ExceptionHandler(value=Exception.class)
	public String handleException(Exception e){
		
		System.out.println("exception 호출" + e.getMessage());
		return "message";
	}
}
