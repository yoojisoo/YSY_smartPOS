package com.ysy.jwt.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ysy/v1")
public class AuthController {

	
	
	
	@GetMapping("/tokenRefresh")
	public String tokenRefresh(@RequestBody String refereshToken, HttpServletResponse response) {
		
		return "";
	}
}
