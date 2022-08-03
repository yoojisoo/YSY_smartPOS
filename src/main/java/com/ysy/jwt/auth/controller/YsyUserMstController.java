package com.ysy.jwt.auth.controller;

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
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.dto.UserDto;
import com.ysy.jwt.auth.service.YsyUserMstService;

import io.swagger.annotations.ApiOperation;

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
@RequestMapping("/ysy/v1")
public class YsyUserMstController {

	@Autowired
	YsyUserMstService ysyUserService;
	
	@PostMapping("/auth/signUp") // 회원가입. 무인증.
	@ResponseBody
	public ResponseAuthDto<String> signUp(@RequestBody JoinDto joinModel) {
		return ysyUserService.signUp(joinModel);
	}
	
	@GetMapping("/auth/isAdmin") // 관리자인지 체크
	public boolean isAdmin(@RequestParam String username) {
		System.out.println();
		return ysyUserService.isAdmin(username);
	}
	
	@PostMapping("/user/modUserInfo") // 회원정보 수정
	@ResponseBody
	public String modUserInfo(@RequestBody UserDto userDto) {
		return ysyUserService.modUserInfo(userDto);
	}
	
	/**
	 * 2022 07 27 s_plus7
	 * 모든 유저, 모든 정보 list
	 * */
	@ApiOperation(value="유저 전체 리스트" , notes="parameter : 검색조건 | 조건 없을 시 전체 가져오기")
	@PostMapping("/manager/getUserListCondition")
	public ResponseAuthDto<UserDto> getUserListCondition (){

		System.out.println("뿅");
//		return null;
		return ysyUserService.getUserListCondition();
	}
	
	
	@GetMapping("/manager/getUserAddr")
	public ResponseAuthDto<UserDto> getUserDetail(@RequestParam String userId){
		return ysyUserService.getUserAddr(userId);
	}
	
	@GetMapping("/manager/getConditionItems")
	public ResponseAuthDto<UserDto> getConditionItems(){
		return ysyUserService.getConditionItems();
	}
	
	/** 2022 07 07 mnew2m
	 * 컨트롤 가능한 모든 유저 조회
	 * userId Param -> 해당 userId보다 낮은 등급인 유저를 조회 */
	@GetMapping("/manager/getFilterUserList")
	public ResponseAuthDto<UserDto> getFilterUserList(@RequestParam String userId) {
		return ysyUserService.getFilterUserList(userId);
	}
}
