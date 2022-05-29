package com.ysy.jwt.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author clubbboy@naver.com
 *  2022. 5. 28.
 *  Desc : 페이지에서 백 눌렀을때 경로 찾아야 한는데...... 어케 찾는거지??
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping("/signIn")
	public String kakaoLogout() {
		
		System.out.println("12345");
		return "logout.html";
	}
}
