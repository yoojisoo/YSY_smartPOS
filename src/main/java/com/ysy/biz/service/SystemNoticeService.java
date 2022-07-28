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
import com.ysy.biz.entity.QSystemNoticeMst;
import com.ysy.biz.entity.SystemNoticeMst;

@Service
//@AllArgsConstructor
public class SystemNoticeService {

	@PersistenceContext
	EntityManager em; // 1
	
	/** 22-07-06 mnew2m
	 * 사용하는 Q Class */
	QSystemNoticeMst qSystemNoticeMst = QSystemNoticeMst.systemNoticeMst; 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public ResponseDto<?> findSystemNotice(int size) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		List<SystemNoticeMst> noticeList =  query
				.selectFrom(qSystemNoticeMst)
				.limit(size)
				.orderBy(qSystemNoticeMst.boardId.desc())
				.fetch();
		
		List<SystemNoticeDto> resultList = new ArrayList<SystemNoticeDto>();
		int loop = 1;
		for(SystemNoticeMst notice : noticeList) {
			resultList.add(new SystemNoticeDto(notice, loop));
			loop++;
		}
		
		return new ResponseDto(resultList, HttpStatus.OK);
	}
}
