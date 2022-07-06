package com.ysy.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.biz.dto.ResponseDto;
import com.ysy.jwt.auth.service.YsyMenuMstService;

@RestController
@RequestMapping("ysy/v1/menu")
//@AllArgsConstructor
public class YsyMenuController {

	@Autowired
	private  YsyMenuMstService ysyMenuMstService;
	
	/** 22-07-05 mnew2m
	 * 로그인 된 아이디가 들어왔을 때,
	 * 로그인을 하지 않았을 때,
	 * 도는 쿼리가 달라서 분리함 ! */
	@GetMapping("/findDefaultMenuList")
	public ResponseDto<?> findDefaultMenuList() {
		return ysyMenuMstService.findDefaultMenuList();
	}
	
	@GetMapping("/findMenuList")
	public ResponseDto<?> findMenuList(@RequestParam String userId) {
		return ysyMenuMstService.findMenuList(userId);
	}
}
