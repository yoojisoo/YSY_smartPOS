package com.ysy.jwt.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.biz.dto.ResponseDto;
import com.ysy.jwt.auth.dto.MenuDto;
import com.ysy.jwt.auth.dto.UserMngDto;
import com.ysy.jwt.auth.service.YsyUserMngService;

/** 매니저 영역 : 내가 컨트롤할 유저 데이터 */
@RestController
@CrossOrigin
@RequestMapping("/ysy/v1")
public class YsyUserMngController {

	
	@Autowired
	YsyUserMngService ysyUserMngService;
	
	/** 2022 06 02 yoojisoo
	  * 모든 유저 조회
	  * size 파라미터 -> 모든 유저정보 size 만큼 조회
	  * manager이상 접근
	  * //파라메터 리퀘스트 dto를 이용하여 여러 조건을 받을 수 있도록 함.
	  */
	@GetMapping("/getUserList")
	public List<UserMngDto> getUserList(@RequestParam int size){
		return ysyUserMngService.getUserList(size);
	}
	
	/** 2022 07 05 yoojisoo 
	 * 유저 1명의 상세정보 조회 
	 * userId 파라미터 -> 해당 userId로 유저 연관 테이블(UserAddress Table) 조회
	 * manager이상 접근
	 */
	@GetMapping("/getUserDetail")
	public UserMngDto getUserDetail(@RequestParam String userId){
		return ysyUserMngService.getUserDetail(userId);
	}
	
	/** 2022 07 07 mnew2m
	 * 컨트롤 가능한 모든 유저 조회
	 * userId Param -> 해당 userId보다 낮은 등급인 유저를 조회 */
	@GetMapping("/getFilterUserList")
	public ResponseDto<UserMngDto> getFilterUserList(@RequestParam String userId) {
		return ysyUserMngService.getFilterUserList(userId);
	}
	
	/** 2022 07 07 mnew2m
	 * 컨트롤 가능한 유저들 중 선택한 유저의 접근가능메뉴 리스트 조회
	 * userId Param -> 해당 userId가 접근가능한 메뉴 리스트를 조회 */
	@GetMapping("/getUserMenuList")
	public ResponseDto<MenuDto> getUserMenuList(@RequestParam String userId) {
		return ysyUserMngService.getUserMenuList(userId);
	}
	
	/** 2022 06 16 yoojisoo delete userInfo from the grid */
//	@PostMapping("/admin/delGridUserInfo")
//	public void delGridUserInfo(@RequestBody List<String> usernameList) {
//		System.out.println("/delGridUserInfo 들어옴 ---------------------------> ");
//		ysyUserMngService.delGridUserInfo(usernameList);
//	}
	
	
	
	
}
