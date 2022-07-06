package com.ysy.jwt.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.jwt.auth.dto.UserMngDto;
import com.ysy.jwt.auth.service.YsyUserMngService;

/** 매니저 영역 : 내가 컨트롤할 유저 데이터 */
@RestController
@CrossOrigin
@RequestMapping("/ysy/v1")
public class YsyUserMngController {

	
	@Autowired
	YsyUserMngService ysyUserMngService;
	
	/** 2022 07 05 yoojisoo
	 * 유저 1명 조회
	 * userId 파라미터 -> 해당 userId로 유저정보(Id, 이름, 생성일, 가입경로) 조회
	 */
	@GetMapping("/getUser")
	public UserMngDto getUser(@RequestParam String userId){
		return ysyUserMngService.getUser(userId);
	}
	
	/** 2022 07 05 yoojisoo 
	 * 유저 1명 조회
	 * userId 파라미터 -> 해당 userId로 유저 연관 테이블(ysyGrpMst & UserAddress Table) 조회
	 */
	@GetMapping("/getUserAddGrp")
	public UserMngDto getUserAddGrp(@RequestParam String userId){
		return ysyUserMngService.getUserAddGrp(userId);
	}
	
	/** 2022 06 02 yoojisoo
	  * 모든 유저 조회
	  * size 파라미터 -> 모든 유저정보 size 만큼 조회
	  */
	@GetMapping("/getUserList")
	public List<UserMngDto> getUserList(@RequestParam int size){
		return ysyUserMngService.getUserList(size);
	}
	
	
	/** 2022 06 16 yoojisoo delete userInfo from the grid */
//	@PostMapping("/admin/delGridUserInfo")
//	public void delGridUserInfo(@RequestBody List<String> usernameList) {
//		System.out.println("/delGridUserInfo 들어옴 ---------------------------> ");
//		ysyUserMngService.delGridUserInfo(usernameList);
//	}
	
	
	
	
}
