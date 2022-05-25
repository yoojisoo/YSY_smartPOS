package com.ysy.jwt.auth.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.MailDto;
import com.ysy.jwt.auth.entity.YsyEmailAuth;
import com.ysy.jwt.auth.repository.YsyEmailAuthRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class YsyMailService {
	
	private JavaMailSender mailSender;
	
	private YsyEmailAuthRepository ysyEmailAuthRepository;
	
	@Autowired
	private YsyUtil util;
	
	public String mailSend(MailDto mailDto)throws Exception {
		if(!util.isNullAndEmpty(mailDto.getEmail())) {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(mailDto.getEmail());
			message.setSubject("[ "+util.PJT_NAME+" ] 회원가입 인증 이메일 도착 😍");
			String key = createKey();
			message.setText(key);
			mailSender.send(message);
			
			YsyEmailAuth yy = YsyEmailAuth.builder()
					.tmpEmail(mailDto.getEmail())
					.tmpEmailKey(key)
					.build();
			
			ysyEmailAuthRepository.save(yy);
			return key;
		} else return "fail";
	}
	
	
	public boolean mailAuth(MailDto mailDto) throws Exception{
		String key = mailDto.getKey();
		YsyEmailAuth ysyEmailAuth = ysyEmailAuthRepository.findByTmpEmail(key);
		if(key.equals(ysyEmailAuth.getTmpEmailKey())) {
			ysyEmailAuthRepository.delete(ysyEmailAuth);
			return true;
		}
		
		return false;
	}
	
	/** 이메일 인증 키 6자리 숫자 만들기 */
	public String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();
		
		for(int i = 0; i < 6; i++) { // 인증 키 6자리 숫자
			key.append((rnd.nextInt(10)));
		}
		return key.toString();
	}
	
//	public String createCode(String key) {
//		return key.substring(0, 3) + " - " + key.substring(3, 6);
//	}
}
