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
import com.ysy.biz.dto.StoreNoticeDto;
import com.ysy.biz.entity.QStoreNoticeMst;
import com.ysy.biz.entity.StoreNoticeMst;

@Service
/** 22-07-05 mnew2m Q Class, DTO 추가 */
public class StoreNoticeService {

	@PersistenceContext
	EntityManager em;
	
	/** 22-07-06 mnew2m
	 * 사용하는 Q Class */
	QStoreNoticeMst qStoreNoticeMst = QStoreNoticeMst.storeNoticeMst;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public ResponseDto<?> findStoreNotice(int size) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		List<StoreNoticeMst> noticeList = query
				.selectFrom(qStoreNoticeMst)
				.limit(size)
				.orderBy(qStoreNoticeMst.boardId.desc())
				.fetch();
		
		List<StoreNoticeDto> resultList = new ArrayList<StoreNoticeDto>();
		int loop = 1;
		for(StoreNoticeMst notice : noticeList) {
			resultList.add(new StoreNoticeDto(notice, loop));
			loop++;
		}
		
		return new ResponseDto(resultList, HttpStatus.OK);
	}
}
