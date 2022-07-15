package com.ysy.jwt.auth.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.config.MailHandler;
import com.ysy.jwt.auth.dto.MailDto;
import com.ysy.jwt.auth.entity.YsyEmailAuth;
import com.ysy.jwt.auth.repository.YsyEmailAuthRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
public class YsyMailService {
	
	private JavaMailSender mailSender;
	private static final String FROM_ADDRESS = "mnew2m@gmail.com";
	
	@Autowired
	private YsyEmailAuthRepository ysyEmailAuthRepository;
	@Autowired
	private YsyUtil util;
	
	public Boolean mailSend(MailDto mailDto) {
		if(!util.isNullAndEmpty(mailDto.getEmail())) {
			
			try {
				MailHandler mailHandler = new MailHandler(mailSender);
	            
	            // 받는 사람
	           mailHandler.setTo(mailDto.getEmail());
	            // 보내는 사람
	           mailHandler.setFrom(YsyMailService.FROM_ADDRESS);
	            // 제목
	           mailHandler.setSubject("[ " + util.PJT_NAME + " ] 회원가입 인증 이메일 도착 😍");
	            // 내용
	           String key = createKey();
	           String params = "email=" + mailDto.getEmail() + "&key=" + key;
	           String htmlContent = "<a href='http://localhost:8000/ysy/v1/mail/mailKeyConfirm?" + params + "'> 인증을 하시려면 여기를 눌러주세요.</a>";
	           mailHandler.setText(htmlContent, true);
	           mailHandler.send();
	           
	           YsyEmailAuth ysyEmailAuth = YsyEmailAuth
	        		   .builder()
	        		   .tmpEmail(mailDto.getEmail())
	        		   .tmpEmailKey(key)
	        		   .build();
	           
	           ysyEmailAuthRepository.save(ysyEmailAuth);
	           return true;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		} else return false;
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
	
	/** user 존재여부 확인 존재 : true */
	public boolean mailKeyConfirm(MailDto mailDto) {
		System.out.println();
//		if(ysyUserRepository.findByUsername(username) == null)
//			return false;
		
		return true;
	}
}
