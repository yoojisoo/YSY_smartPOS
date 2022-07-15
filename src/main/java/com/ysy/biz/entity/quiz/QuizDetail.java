package com.ysy.biz.entity.quiz;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author clubbboy@naver.com
 * 퀴즈 상세 내역 및 정답/해설
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "QUIZ_DTL")
//@Entity
public class QuizDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long quizDtlId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "" , referencedColumnName = "")
	private QuizMst quizMst; 
	
	private String content;//문제
	
	private String answer;//정답
	
	private String Commentary;// 해설
}
