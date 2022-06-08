package com.ysy.jwt.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.jwt.auth.dto.JoinDto;
import com.ysy.jwt.auth.dto.ModUserDto;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;
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
	
	@PostMapping("/modUserInfo") // 회원정보 수정
	@ResponseBody
	public String modUserInfo(@RequestBody ModUserDto modUserDto) {
		return ysyUserService.modUserInfo(modUserDto);
	}
	
	
	@Autowired
	private YsyUserMstRepository ysyUserRepository;
	
	/** 2022 06 02 yjs  get data sample */
	@GetMapping("/test/getUserList")
	public List<YsyUserMst> getUserList(@RequestParam String id){
//		id = "mnew2m@gmail.com";
		System.out.println("뿅");
		
		List<YsyUserMst> list = ysyUserRepository.findAll();
		list.get(0).getAddressList();
		return list;
	}
	
	
	
}
