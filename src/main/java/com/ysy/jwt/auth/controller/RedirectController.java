package com.ysy.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.ysy.jwt.auth.dto.MailDto;
import com.ysy.jwt.auth.service.YsyMailService;

@Controller
public class RedirectController {

	@Autowired
	private YsyMailService mailService;
	
	@RequestMapping("/kakaoLogin/getCode")
	public RedirectView getKakaoCode(String code) {
		RedirectView rv = new RedirectView("/?code="+code+"&gubun=kakao");
	      rv.setExposeModelAttributes(true);
		return rv;
	}
	@RequestMapping("/naverLogin/getCode")
	public RedirectView setNaverCode(String code) {
		RedirectView rv = new RedirectView("/?code="+code+"&gubun=naver");
		rv.setExposeModelAttributes(true);
		return rv;
	}
	@RequestMapping("/mailAuthCode")
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
