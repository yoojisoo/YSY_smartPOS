package com.ysy.jwt.auth.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.dto.ScheduleDto;
import com.ysy.jwt.auth.entity.QYsyScheduleMst;
import com.ysy.jwt.auth.entity.YsyScheduleMst;

@Service
public class YsyScheduleService {

	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JPAQueryFactory query = new JPAQueryFactory(em);
	
	private QYsyScheduleMst qYsyScheduleMst = QYsyScheduleMst.ysyScheduleMst;
	
	@Transactional
	public void selectScheduleList() {
		String userId = "";
		query.select(qYsyScheduleMst)
		.from(qYsyScheduleMst)
		.where(qYsyScheduleMst.isPublic.eq(true)
		   .or(qYsyScheduleMst.ysyUserMst.username.eq(userId))
			  )
		.fetch();  
	}
	
	@Transactional
	public void createSchdule(ScheduleDto scheduleDto) {
		
		String userId = "";
		int cnt = em.createNativeQuery("INSERT INTO "
				+ "ysy_board_mst ( gubun , title , sub_title, content , user_id "
				+ "             , s_date , e_date , table_id , is_public , color "
				+ "             , reg_id , reg_dt)"
				+ "       VALUES (?,?,?,?,?,?,?,?,?,?,? , now() )")
			    .setParameter(1, scheduleDto.getGubun())
			    .setParameter(2, scheduleDto.getTitle())
			    .setParameter(3, scheduleDto.getSubTitle())
			    .setParameter(4, scheduleDto.getContent())
			    .setParameter(5, userId)
			  	.setParameter(6, scheduleDto.getSDate())
			  	.setParameter(7, scheduleDto.getEDate())
			  	.setParameter(8, scheduleDto.getTableId())
			  	.setParameter(9, scheduleDto.isPublic())
			  	.setParameter(10,scheduleDto.getColor())
			  	.setParameter(11,userId)
			  	.executeUpdate();
		
	}
	
	@Transactional
	public void updateSchdule(ScheduleDto scheduleDto) {
		long sid = 0L;
		YsyScheduleMst ysyScheduleMst =
				query.select(qYsyScheduleMst)
					 .from(qYsyScheduleMst)
					 .where(qYsyScheduleMst.sId.eq(sid))
					 .fetchOne()
					 ;
		ysyScheduleMst.setTitle(scheduleDto.getTitle());
	}
	
	
	
	
	
	
	
	
	
	
	

	}
