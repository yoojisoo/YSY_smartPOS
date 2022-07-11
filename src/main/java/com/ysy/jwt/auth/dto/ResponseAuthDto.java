package com.ysy.jwt.auth.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAuthDto<T> {

	private HttpStatus status;
	private List<T> dataList;
	private T data;
	private String msg;
	
	/** list data return */
	public ResponseAuthDto(List<T> dataList , HttpStatus status){
		this.dataList = dataList;
		this.status = status;
	}
	
	/** data return */
	public ResponseAuthDto(T data , HttpStatus status){
		this.data = data;
		this.status = status;
	}
	
	/** error response return */
	public ResponseAuthDto(String msg , HttpStatus status){
		this.msg = msg;
		this.status = status;
	}
	
	/*
	 * return new ResponseDto<Integer>(HttpStatus.OK.value,1)
	 * */
}