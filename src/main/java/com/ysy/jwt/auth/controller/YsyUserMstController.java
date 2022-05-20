package com.ysy.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.jwt.auth.dto.JoinDto;
import com.ysy.jwt.auth.service.YsyUserMstService;

/**
 * @author clubbboy@naver.com
 *  2022. 5. 1
 *  Desc : 회원가입관련 컨트롤러
 *         회원가입시 default로 관리되어야할 것들 셋팅.
 *         회원가입시 Role은 default로 USER_ROLE로 셋팅
 * 
 * @RestController : @Controller에 @ResponseBody가 결합된 어노테이션
 * 컨트롤러 클래스 하위 메서드에 @ResponseBody 어노테이션을 붙이지 않아도
 * 문자열과 JSON 등을 전송할 수 있다.
 * 
 */
@RestController
@CrossOrigin
@RequestMapping("/ysy/v1/auth")
public class YsyUserMstController {

	
	@Autowired
	YsyUserMstService ysyUserService;
	
	
	
	@PostMapping("/signUp")//회원가입. 무인증.
	@ResponseBody
	public String signUp(@RequestBody JoinDto joinModel) {
		
		return ysyUserService.signUp(joinModel);
	}
	
	
	
	
}
