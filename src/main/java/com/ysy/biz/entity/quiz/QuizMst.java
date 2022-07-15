package com.ysy.biz.entity.quiz;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author clubbboy@naver.com
 * 퀴즈 마스터 
 *  분류를 통해서 여러 문제를 관리해야하는데...
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "QUIZ_MST")
//@Entity
public class QuizMst extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PK PK;
	
	@Column(name="PROBLEM" , length=255 , nullable = false)
	private String problem;//문제
	
	@Column(name="DIFFICULTY" , length=10 , nullable = false)
	private String difficulty;//난이도
	
	@Column(name="P_TYPE" , length=10 , nullable = false)
	private boolean pType;//주관식 객관식 여부
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable //pk 여러개 생성시 사용 : @EmbeddedId 
	public static class  PK implements Serializable{
		
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		
		@Column(name="GUBUN" , length=50 , nullable = false)
		private String gubun;//분류
		
	}
}
