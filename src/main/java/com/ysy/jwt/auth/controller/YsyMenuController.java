package com.ysy.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.biz.dto.ResponseDto;
import com.ysy.jwt.auth.dto.MenuDto;
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.model.PrincipalDetails;
import com.ysy.jwt.auth.service.YsyMenuMstService;

@RestController
@RequestMapping("/ysy/v1")
/** findDefaultMenuList -> 로그인 X -> 권한 X
 * findMenuList -> 로그인 O -> user 권한 O */
public class YsyMenuController {

	@Autowired
	private  YsyMenuMstService ysyMenuMstService;
	
	/** 22-07-05 mnew2m
	 * 로그인 된 아이디가 들어왔을 때,
	 * 로그인을 하지 않았을 때,
	 * 도는 쿼리가 달라서 분리함 ! */
	@GetMapping("/findDefaultMenuList")
	public ResponseDto<MenuDto> findDefaultMenuList() {
		
		return ysyMenuMstService.findDefaultMenuList();
	}
	
	@GetMapping("/user/findMenuList")
	public ResponseDto<MenuDto> findMenuList(@RequestParam String userId , @AuthenticationPrincipal PrincipalDetails p ) {
		System.out.println("findMenuList ysyUserMst.toString()"+p.getUser().getUsername());
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		YsyUserMst user = ((PrincipalDetails)authentication.getPrincipal()).getUser();
		return ysyMenuMstService.findMenuList(p.getUser().getUsername());
	}
	
	/** 2022 07 07 mnew2m
	 * 컨트롤 가능한 유저들 중 선택한 유저의 접근가능메뉴 리스트 조회
	 * userId Param -> 해당 userId가 접근가능한 메뉴 리스트를 조회 */
	@GetMapping("/manager/getFilterMenuList")
	public ResponseAuthDto<MenuDto> getFilterMenuList(@RequestParam String userId) {
		return ysyMenuMstService.getFilterMenuList(userId);
	}
}
