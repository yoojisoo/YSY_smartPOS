package com.ysy.biz.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

	HttpStatus status;
	T data;
	
	/*
	 * return new ResponseDto<Integer>(HttpStatus.OK.value,1)
	 * */
}
