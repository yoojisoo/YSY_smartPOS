package com.ysy.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.entity.YsyBiz;
import com.ysy.jwt.auth.entity.YsyUser;
import com.ysy.jwt.auth.service.YsyBizService;
import com.ysy.jwt.auth.service.YsyUserService;

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
public class YsyUserController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	YsyUserService ysyUserService;
	
	@Autowired
	YsyBizService ysyBizService;
	
	@PostMapping("/signUp")//회원가입. 무인증.
	@ResponseBody
	public String signUp(@RequestBody YsyUser ysyUser ) {
		
		if( !YsyUtil.isNullAndEmpty(ysyUser.getUsername()) &&
		    !YsyUtil.isNullAndEmpty(ysyUser.getPassword()) &&
		    !YsyUtil.isNullAndEmpty(ysyUser.getName())) 
		{
			
			
			if(ysyUserService.isUser(ysyUser.getUsername())) {
				return "error : user 존재";
			}
			
			String bizCd = "0001";
			YsyBiz ysyBiz = null;
			if(!ysyBizService.isBizCd(bizCd)) {
				ysyBiz = YsyBiz.builder()
						.bizCd(bizCd)
						.bizNm("")
						.useYn("Y")
						.delYn("N").build();
				if(!ysyBizService.createBiz(ysyBiz)) {
					return "회사코드가 있습니다.";
				}
			}
			
			
			ysyUser.setPassword(bCryptPasswordEncoder.encode(ysyUser.getPassword()));
			ysyUser.setRoles("ROLE_USER");//기본 룰 셋팅 , 이후 관리자 페이지에서 role 변경
			ysyUser.setYsyBiz(ysyBiz);
			
			
			if(ysyUserService.signUp(ysyUser )) {
				return "ok -> login page move!";
			}
			else return "error : user register error!";
		}
		else {
			return "error : Id or password or name is empty!";
		}
	}
	
//	public boolean createBiz(String bizCd) {
//		YsyBiz ysyBiz = null;
//		if(!ysyBizService.isBizCd(bizCd)) {
//			ysyBiz = YsyBiz.builder()
//					.bizCd(bizCd)
//					.bizNm("")
//					.useYn("Y")
//					.delYn("N").build();
//			if(!ysyBizService.createBiz(ysyBiz)) {
//				return "회사코드가 있습니다.";
//			}
//		}
//		
//		return treu;
//	}
	
	
	
}
