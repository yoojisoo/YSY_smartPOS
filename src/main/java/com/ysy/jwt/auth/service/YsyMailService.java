package com.ysy.jwt.auth.service;

import java.util.Properties;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.MailDto;
import com.ysy.jwt.auth.entity.YsyEmailAuth;
import com.ysy.jwt.auth.repository.YsyEmailAuthRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YsyMailService {
	
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("mnew2m@gmail.com");
        javaMailSender.setPassword("gsq5193sq");
        javaMailSender.setPort(587);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }
    
    private Properties getMailProperties() {
        Properties pt = new Properties();
        pt.put("mail.smtp.socketFactory.port", 465);
        pt.put("mail.smtp.auth", true);
        pt.put("mail.smtp.starttls.enable", true);
        pt.put("mail.smtp.starttls.required", true);
        pt.put("mail.smtp.socketFactory.fallback",false);
        pt.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return pt;
    }
    
//	private final JavaMailSender mailSender;
	@Autowired
	private YsyEmailAuthRepository ysyEmailAuthRepository;
//	@Autowired
//	private YsyUserMstRepository ysyUserRepository;
	
	@Autowired
	private YsyUtil util;
	
	public Boolean mailSend(MailDto mailDto)throws Exception {
		if(!util.isNullAndEmpty(mailDto.getEmail())) {
//			SimpleMailMessage message = new SimpleMailMessage();
			MimeMessage message = javaMailService().createMimeMessage();
//			message.setTo(mailDto.getEmail());
			message.addRecipients(RecipientType.TO, mailDto.getEmail());
			message.setSubject("[ " + util.PJT_NAME + " ] 회원가입 인증 이메일 도착 😍");
			String key = createKey();
			String params = "email=" + mailDto.getEmail() + "&key=" + key;
			String htmlStr = "";
			htmlStr =  "<a href='http://localhost:8000/ysy/v1/mail/mailKeyConfirm?" + params + "'> 인증을 하시려면 여기를 눌러주세요.</a>";
			message.setText(htmlStr, "utf-8", "html");
			javaMailService().send(message);
			
			YsyEmailAuth yy = YsyEmailAuth.builder()
					.tmpEmail(mailDto.getEmail())
					.tmpEmailKey(key)
					.build();
			
			ysyEmailAuthRepository.save(yy);
			return true;
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
	
//		if(ysyUserRepository.findByUsername(username) == null)
//			return false;
		
		return true;
	}
	
//	public String createCode(String key) {
//		return key.substring(0, 3) + " - " + key.substring(3, 6);
//	}
}
