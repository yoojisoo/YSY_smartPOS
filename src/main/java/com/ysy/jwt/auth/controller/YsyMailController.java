package com.ysy.jwt.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ysy.jwt.auth.dto.MailDto;
import com.ysy.jwt.auth.service.YsyMailService;
import com.ysy.jwt.auth.service.YsyUserMstService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ysy/v1/auth")
public class YsyMailController {
	private final YsyMailService mailService;
	private final YsyUserMstService userService;
	
	@PostMapping("/mailConfirm")
	@ResponseBody
	public Boolean confirmMail(@RequestBody MailDto mailDto) {
		System.out.println(userService.isUser(mailDto.getEmail()));
		return userService.isUser(mailDto.getEmail());
	}
	
	@PostMapping("/mailSend")
	@ResponseBody
	public String execMail(@RequestBody MailDto mailDto) throws Exception {
		return mailService.mailSend(mailDto);
	}
}
