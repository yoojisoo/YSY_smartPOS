package com.ysy.jwt.auth.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.biz.dto.ResponseDto;
import com.ysy.jwt.auth.dto.MenuDto;
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.model.PrincipalDetails;
import com.ysy.jwt.auth.service.YsyMenuMstService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ysy/v1")
/** findDefaultMenuList -> 로그인 X -> 권한 X
 * findMenuList -> 로그인 O -> user 권한 O */
public class YsyMenuController {
	
	private final Logger logger = LogManager.getLogger(YsyMenuController.class);
	
	
	
	@Autowired
	private  YsyMenuMstService ysyMenuMstService;
	
	@ApiOperation(value = " 메뉴 리스트 조회 - 파리미터 없음" 
			    , notes = "조회시 로그인 된 사람의 권한에 따라 메뉴 리스트 조회됨.\n"
			    		+ "로그인 안된 사람은 default menu 조회됨. \n"
			    		+ "return  type = List<MenuDto> ")
	@GetMapping("/findMenuList")
	public ResponseDto<MenuDto> findDefaultMenuList(@AuthenticationPrincipal PrincipalDetails p) {
		System.out.println("findDefaultMenuList system.out###########################################");
		System.out.println("findDefaultMenuList system.out###########################################");
		logger.info("info*************************************************************************");
		logger.info("info*************************************************************************");
		logger.debug("debug$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		logger.debug("debug$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		logger.debug("Hello Debug level log");
        logger.warn("Warn @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        logger.warn("Warn @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        logger.error("error %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        logger.error("error %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		if(p ==null || p.getUser() == null || p.getUser().getUsername().isEmpty()) {
			return ysyMenuMstService.findDefaultMenuList();
		}else {
			return ysyMenuMstService.findMenuList(p.getUser().getUsername());
		}
		
	}
	
	/** 2022 07 07 mnew2m
	 * 컨트롤 가능한 유저들 중 선택한 유저의 접근가능메뉴 리스트 조회
	 * userId Param -> 해당 userId가 접근가능한 메뉴 리스트를 조회 */
	@ApiOperation(value = " 메뉴 권한관리 - parameter - userId" 
		    , notes = " 메뉴 권한 관리시 해당 유저의 접근 가능한 메뉴 정보 리스트\n"
		    		+ "return  type = List<MenuDto> ")
	@GetMapping("/manager/getFilterMenuList")
	public ResponseAuthDto<MenuDto> getFilterMenuList(@RequestParam String userId) {
		return ysyMenuMstService.getFilterMenuList(userId);
	}
}
