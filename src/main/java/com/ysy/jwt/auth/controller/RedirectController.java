package com.ysy.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.ysy.jwt.auth.dto.MailDto;
import com.ysy.jwt.auth.service.YsyMailService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author clubbboy@naver.com
 * desc : 다른 쪽에서 url 호출시 spa특성상 index.html로 redirect하고
 *        index.html에서 param에 따라 해당 페이지로 이동시켜 줌.
 *
 */
@Controller
public class RedirectController {

	@Autowired
	private YsyMailService mailService;
	
	@ApiOperation(value = "kakao login시 redirect 함수"
			     ,notes = "카카오 로그인시 카카오에서 콜백받는 함수. 해당함수 요청 후 client의 카카오 로그인 페이지로 이동해서 로그인 진행됨")
	@GetMapping("/kakaoLogin/getCode")
	public RedirectView getKakaoCode(String code) {
		RedirectView rv = new RedirectView("/?code="+code+"&gubun=kakao");
	      rv.setExposeModelAttributes(true);
		return rv;
	}
	
	@ApiOperation(value = "naver login시 redirect 함수"
		         ,notes = "네이버 로그인시 카카오에서 콜백받는 함수. 해당함수 요청 후 client의 네이버 로그인 페이지로 이동해서 로그인 진행됨")
	@GetMapping("/naverLogin/getCode")
	public RedirectView setNaverCode(String code) {
		RedirectView rv = new RedirectView("/?code="+code+"&gubun=naver");
		rv.setExposeModelAttributes(true);
		return rv;
	}
	
	@ApiOperation(value = "회원가입시 메일에서 인증시 호출 함수"
	             ,notes = "메일 인증시 해당 페이지로 접근하여 메일 인증 확인 후 로그인 페이지로 이동함.")
	@GetMapping("/mailAuthCode")
	public RedirectView mailAuthCode(String email , String authKey) {
		//인증처리 
		boolean flag = mailService.mailKeyConfirm(new MailDto(email,authKey));
		
		if(flag) {
			RedirectView rv = new RedirectView("/?gubun=email&status=true&msg=email auth complete!");
			rv.setExposeModelAttributes(true);
			return rv;
		}
		else {
			RedirectView rv = new RedirectView("/?gubun=email&status=false&msg=email auth error!");
			rv.setExposeModelAttributes(true);
			return rv;
		}
		
//		RedirectView rv = new RedirectView("/?code="+code+"&oauth_path=mail");
//		rv.setExposeModelAttributes(true);
//		return rv;
	}


}
