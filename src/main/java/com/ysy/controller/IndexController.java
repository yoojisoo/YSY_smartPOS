package com.ysy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.service.TestService;


/**
 * @RestController : @Controller에 @ResponseBody가 결합된 어노테이션
 * 컨트롤러 클래스 하위 메서드에 @ResponseBody 어노테이션을 붙이지 않아도
 * 문자열과 JSON 등을 전송할 수 있다.
 * */
@RestController
@RequestMapping("/ysy/v1/admin")
public class IndexController {

	
	@GetMapping("/testData")
	public String testData() {
		
		
		return "admin 권한 잘 호출됨";
	}
	
	
	
	/**
	 * @Autowired를 설정한 메서드가 자동으로 호출되고, 인스턴스가 자동으로 주입된다.
	 * 즉, 해당 변수 및 메서드에 스프링이 관리하는 Bean을 자동으로 매핑해주는 개념
	 * */
	@Autowired
	private TestService service;
	
	
	
}
