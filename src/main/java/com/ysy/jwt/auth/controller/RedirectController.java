package com.ysy.jwt.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {

	@RequestMapping("/kakaoLogin/getCode")
	public RedirectView getKakaoCode(String code) {
		RedirectView rv = new RedirectView("/?code="+code+"&oauth_path=kakao");
	      rv.setExposeModelAttributes(true);
		return rv;
	}
	@RequestMapping("/naverLogin/getCode")
	public RedirectView setNaverCode(String code) {
		RedirectView rv = new RedirectView("/?code="+code+"&oauth_path=naver");
		rv.setExposeModelAttributes(true);
		return rv;
	}


}
