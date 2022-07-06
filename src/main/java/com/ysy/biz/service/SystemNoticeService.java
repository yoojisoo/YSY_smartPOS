package com.ysy.biz.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.biz.dto.ResponseDto;
import com.ysy.biz.dto.SystemNoticeDto;
import com.ysy.biz.entity.QSystemNotice;
import com.ysy.biz.entity.SystemNotice;

@Service
//@AllArgsConstructor
public class SystemNoticeService {

	@PersistenceContext
	EntityManager em; // 1
	
	/** 22-07-06 mnew2m
	 * 사용하는 Q Class */
	QSystemNotice qSystemNotice = QSystemNotice.systemNotice; 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public ResponseDto<?> findSystemNotice(int size) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		List<SystemNotice> noticeList =  query
				.selectFrom(qSystemNotice)
				.limit(size)
				.orderBy(qSystemNotice.boardId.desc())
				.fetch();
		
		List<SystemNoticeDto> resultList = new ArrayList<SystemNoticeDto>();
		int loop = 1;
		for(SystemNotice notice : noticeList) {
			resultList.add(new SystemNoticeDto(notice, loop));
			loop++;
		}
		
		return new ResponseDto(resultList, HttpStatus.OK);
	}
}
