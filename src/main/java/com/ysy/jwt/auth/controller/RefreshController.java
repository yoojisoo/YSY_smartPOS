package com.ysy.jwt.auth.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author clubbboy@naver.com
 *  2022. 7. 31.
 *  Desc  : spa의 경우 새로고침시 스프링의 url로 콜이 들어가는데 
 *  이를 모두 잡아서 index.html을 바라보게 함.
 *  그러면 router가 알아서 작동?
  */
@Controller
public class RefreshController  implements ErrorController{

	@GetMapping("/error")
	public String redirectRoot() {
		
		return "index.html";
	}
}
