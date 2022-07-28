package com.ysy.biz.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.biz.dto.QnADto;
import com.ysy.biz.dto.ResponseDto;
import com.ysy.biz.dto.StoreNoticeDto;
import com.ysy.biz.entity.QQnA;
import com.ysy.biz.entity.QnA;
import com.ysy.biz.entity.StoreNotice;

@Service
/** 22-07-28 mnew2m Q Class, DTO 추가 */
public class QnAService {

	@PersistenceContext
	EntityManager em;
	
	/** 22-07-06 mnew2m
	 * 사용하는 Q Class */
	QQnA qQnA = QQnA.qnA;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public ResponseDto<?> findQnA(int size) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		List<QnA> qnaList = query
				.selectFrom(qQnA)
				.limit(size)
				.orderBy(qQnA.boardId.desc())
				.fetch();
		
		List<QnADto> resultList = new ArrayList<QnADto>();
		int loop = 1;
		for(QnA qna : qnaList) {
			resultList.add(new QnADto(qna, loop));
			loop++;
		}
		
		return new ResponseDto(resultList, HttpStatus.OK);
	}
}
