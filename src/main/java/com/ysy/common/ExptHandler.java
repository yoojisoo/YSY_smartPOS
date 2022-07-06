package com.ysy.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.biz.dto.ResponseDto;

@ControllerAdvice
@RestController
public class ExptHandler {

	
	
	
	@ExceptionHandler(value=Exception.class)
//	@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Data Not Found111")
	public ResponseDto<Exception> handleException(Exception e){
		
		System.out.println("exception 호출 user = " + e.getMessage());
		return new ResponseDto<Exception>(e.getMessage() , HttpStatus.BAD_REQUEST);
	}
}
