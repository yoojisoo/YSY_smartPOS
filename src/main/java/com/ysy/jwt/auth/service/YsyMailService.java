package com.ysy.jwt.auth.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.MailDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class YsyMailService {
	
	private JavaMailSender mailSender;
	private static final String KEY = createKey();
	
	@Autowired
	private YsyUtil util;
	
	public String mailSend(MailDto mailDto)throws Exception {
		if(!util.isNullAndEmpty(mailDto.getEmail())) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(mailDto.getEmail());
			message.setSubject("[ SPOS ] 회원가입 인증 이메일 도착 😍");
			message.setText(createCode(KEY));
			mailSender.send(message);
			
			return KEY;
		} else return "fail";
	}
	
	/** 이메일 인증 키 6자리 숫자 만들기 */
	public static String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();
		
		for(int i = 0; i < 6; i++) { // 인증 키 6자리 숫자
			key.append((rnd.nextInt(10)));
		}
		
		return key.toString();
	}
	
	public String createCode(String key) {
		return key.substring(0, 3) + " - " + key.substring(3, 6);
	}
}
