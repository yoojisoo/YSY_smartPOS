package com.ysy.biz.entity.quiz;

import javax.persistence.Table;

import com.ysy.jwt.auth.entity.base.BaseEntity;

/**
 * 
 * @author clubbboy
 * 퀴즈 푼 사람들... 통계...
 */
@Table(name = "QUIZ_HISTORY")
public class QuizHistory extends BaseEntity{

	private long id;
	
	private String userId;
	
	private String quizId;
	
	private String answerRegDt;//문제 푼 일자.
	
	private boolean isAnswer;//정답 맞췄는지 여부.
}
