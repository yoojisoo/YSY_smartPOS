package com.ysy.biz.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.biz.dto.StoreNoticeDto;
import com.ysy.biz.entity.QStoreNotice;
import com.ysy.biz.entity.StoreNotice;

@Service
//@AllArgsConstructor
/** 22-07-05 mnew2m Q Class, DTO 추가 */
public class StoreNoticeService {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public List<StoreNoticeDto> findStoreNotice(int size) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		QStoreNotice qStoreNotice = QStoreNotice.storeNotice;
		List<StoreNotice> noticeList = query
				.selectFrom(qStoreNotice)
				.limit(size)
				.orderBy(qStoreNotice.boardId.desc())
				.fetch();
		
		List<StoreNoticeDto> resultList = new ArrayList<StoreNoticeDto>();
		int loop = 1;
		for(StoreNotice notice : noticeList) {
			resultList.add(new StoreNoticeDto(notice, loop));
			loop++;
		}
		
		return resultList;
	}
}
