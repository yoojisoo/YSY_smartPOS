package com.ysy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test21")
public class Test2Ctrl {

	
	
	@GetMapping("/testData")
	public String testData() {
		
		System.out.println("testData 진입");
		
		return "Test2Ctrl testData";
	}
}
