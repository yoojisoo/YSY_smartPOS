package com.ysy.biz.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.biz.dto.SystemNoticeDto;
import com.ysy.biz.entity.QSystemNotice;
import com.ysy.biz.entity.SystemNotice;
import com.ysy.biz.repository.SystemNoticeRepository;

@Service
//@AllArgsConstructor
public class SystemNoticeService {

	@Autowired
	private SystemNoticeRepository systemNoticeRepository;
	
	@PersistenceContext
	EntityManager em; // 1
	
	@Transactional
	public List<SystemNoticeDto> findSystemNotice(int size) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		QSystemNotice qSystemNotice = QSystemNotice.systemNotice;
		List<SystemNotice> noticeList =  query.selectFrom(qSystemNotice)
				.limit(size)
				.orderBy(qSystemNotice.boardId.desc())
				.fetch();
		
		List<SystemNoticeDto> resultList = new ArrayList<SystemNoticeDto>();
		int loop = 1;
		for(SystemNotice notice :noticeList) {
			resultList.add(new SystemNoticeDto(notice , loop));
			loop++;
		}
		
		
		return resultList;
	}
	

}
