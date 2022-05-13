package com.ysy.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.model.Member;
import com.ysy.model.TestModel;
import com.ysy.service.TestService;

/**
 * @RestController : @Controller에 @ResponseBody가 결합된 어노테이션
 * 컨트롤러 클래스 하위 메서드에 @ResponseBody 어노테이션을 붙이지 않아도
 * 문자열과 JSON 등을 전송할 수 있다.
 * */
@RestController
@RequestMapping(value = "/ysy/v1/user" , method = RequestMethod.GET)
public class TestCtrl3 {
	
	
//	@GetMapping(value = "/gettestData1" )
//	public String gettestData1() {
//		
//		System.out.println("123456");
//		return "get방식 user 권한 잘 호출됨";
//	}
//	
		
}
