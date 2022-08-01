package com.ysy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.entity.QYsyBizMst;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
import com.ysy.jwt.auth.entity.QYsyUserAddress;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyGrpMst;

@SpringBootTest
@RunWith(SpringRunner.class)
public class YoojisooTest {
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	JPAQueryFactory query = new JPAQueryFactory(em);
	
	QYsyUserMst     qYsyUserMst 	= QYsyUserMst.ysyUserMst;
	QYsyGrpMst     qYsyGrpMst 	= QYsyGrpMst.ysyGrpMst;
	QYsyGrpMenuMap 	qYsyGrpMenuMap  = QYsyGrpMenuMap.ysyGrpMenuMap;
	QYsyUserAddress qYsyUserAddress = QYsyUserAddress.ysyUserAddress;	
	QYsyBizMst		qYsyBizMst 		= QYsyBizMst.ysyBizMst;	
	
	@Test
	@Transactional
	public void geDataTest() {
		java.util.List<String> grpList = query
		.select(qYsyGrpMst.grpNm)
		.from(qYsyGrpMst)
		.where(qYsyGrpMst.grpPK.bizCd.eq("0001"))
		.fetch();
		
		List<YsyGrpMst> grpList2 = query
				.select(qYsyGrpMst)
				.from(qYsyGrpMst)
				.where(qYsyGrpMst.grpPK.bizCd.eq("0001"))
				.fetch();
		
		System.out.println("grpList 테스트 ====================>");
		System.out.println(grpList);
		System.out.println(grpList2);
		
	}

}
