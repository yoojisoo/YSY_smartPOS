package com.ysy.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ysy.jwt.auth.dto.MailDto;
import com.ysy.jwt.auth.service.YsyMailService;
import com.ysy.jwt.auth.service.YsyUserMstService;

import lombok.RequiredArgsConstructor;

@Controller
//@RequiredArgsConstructor
@RequestMapping("/ysy/v1/mail")
public class YsyMailController {
	
	@Autowired
	private YsyMailService mailService;
	@Autowired
	private YsyUserMstService userService;
	
	@PostMapping("/mailConfirm")
	@ResponseBody
	public Boolean mailConfirm(@RequestBody MailDto mailDto) {
		return userService.isUser(mailDto.getEmail());
	}
	
	@PostMapping("/mailSend")
	@ResponseBody
	public Boolean mailSend(@RequestBody MailDto mailDto) throws Exception {
		return mailService.mailSend(mailDto);
	}
	
	@GetMapping("/mailKeyConfirm")
//	@ResponseBody
	public Boolean mailKeyConfirm(@RequestParam MailDto mailDto) {
		return mailService.mailKeyConfirm(mailDto);
	}
}
