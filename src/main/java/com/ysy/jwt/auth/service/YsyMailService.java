package com.ysy.jwt.auth.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ysy.jwt.auth.common.YsyUtil;
import com.ysy.jwt.auth.config.SystemConfig;
import com.ysy.jwt.auth.dto.MailDto;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.handler.MailHandler;

import lombok.AllArgsConstructor;

@Service
//@AllArgsConstructor
//@RequiredArgsConstructor
public class YsyMailService {
	
	private JavaMailSender mailSender;
	
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JPAQueryFactory   query = new JPAQueryFactory(em);
	
	private QYsyUserMst qYsyUserMst = QYsyUserMst.ysyUserMst;
	
	@Autowired
	private YsyUtil util;
	
	public Boolean mailSend(MailDto mailDto) {
		if(!util.isNullAndEmpty(mailDto.getEmail())) {
			
			try {
				MailHandler mailHandler = new MailHandler(mailSender);
	            
	            // 받는 사람
	           mailHandler.setTo(mailDto.getEmail());
	            // 보내는 사람
	           mailHandler.setFrom(SystemConfig.FROM_ADDRESS);
	            // 제목
	           mailHandler.setSubject("[ " + util.PJT_NAME + " ] 회원가입 인증 이메일 도착 😍");
	            // 내용
	           String key = util.createUUID();
	           
	           JPAUpdateClause update = new JPAUpdateClause(em, qYsyUserMst);
	           update.set(qYsyUserMst.emailKey , key)
	             	 .where(qYsyUserMst.username.eq(mailDto.getEmail()))
	             	 .execute();
	           
	           String params = "email=" + mailDto.getEmail() + "&key=" + key;
	           String htmlContent = "<a href='http://"+SystemConfig.SEVER_URL+"/mailAuthCode?" + params + "'> 인증을 하시려면 여기를 눌러주세요.</a>";
	           mailHandler.setText(htmlContent, true);
	           mailHandler.send();
	           
	           return true;
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		} else return false;
	}
	
	
	
	public boolean mailKeyConfirm(MailDto mailDto) {
		
		YsyUserMst result = query.select(qYsyUserMst)
								.from(qYsyUserMst)
								.where(qYsyUserMst.username.eq(mailDto.getEmail())
									  ,qYsyUserMst.emailKey.eq(mailDto.getKey())
									  )
								.limit(1L)
								.fetchOne()
								;
		System.out.println("mailKeyConfirm email = [" + mailDto.getEmail() + "] == [" + result.getUsername() + "]");
		return result == null || result.getUsername().isEmpty()? false : true;
	}
}
