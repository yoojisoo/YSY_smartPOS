package com.ysy.jwt.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.jwt.auth.dto.FileDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/file")
public class MultipartController {

	
	
	@ApiOperation(value="image and text data 전송" , notes="image와 대량의 text data DB에 저장하기 sample")
	@PostMapping("/multiUpload")
	public String multiUpload(@RequestPart FileDto fileDto) {
		
		
		return "1234";
	}
	
}
