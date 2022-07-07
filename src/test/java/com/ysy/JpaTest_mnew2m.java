package com.ysy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyUserMst;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaTest_mnew2m {
	
	@PersistenceContext
	EntityManager em;
	
	/** 사용하는 Q Class List */
	QYsyUserMst        qYsyUserMst = QYsyUserMst.ysyUserMst;
	QYsyGrpMst         qYsyGrpMst  = QYsyGrpMst.ysyGrpMst;
	QYsyGrpMenuMap qYsyGrpMenuMap  = QYsyGrpMenuMap.ysyGrpMenuMap;
	
	@Test
	@Transactional
	public void getUserList() {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		/** 나의 정보 start */
		String userId = "clubbboy@naver.com";
//		YsyUserMst myInfo = query
//				.selectFrom(qYsyUserMst)
//				.where(qYsyUserMst.username.eq(userId))
//				.fetchOne();
//		
//		String bizCd = myInfo.getYsyGrpMst().getGrpPK().getBizCd();
//		int  levelId = myInfo.getYsyGrpMst().getLevelId();
//		
//		System.out.println();
		/** 나의 정보 end */
		
		
		/** 나보다 낮은 등급의 사용자 정보 start */
//		List<YsyUserMst> userList = query
//				.selectFrom(qYsyUserMst)
//				.where(qYsyUserMst.username.ne(userId)
//						.and(qYsyUserMst.ysyGrpMst.grpPK.bizCd.eq(bizCd))
//						.and(qYsyUserMst.ysyGrpMst.levelId.goe(levelId)))
//				.fetch();
//		
//		System.out.println();
		/** 나보다 낮은 등급의 사용자 정보 end */
		
		YsyUserMst userInfo = query
				.selectFrom(qYsyUserMst)
				.where(qYsyUserMst.username.eq(userId))
				.fetchOne();
		
		int levelId = userInfo.getYsyGrpMst().getLevelId();
		
		List<YsyGrpMenuMap> menuList = query
				.selectFrom(qYsyGrpMenuMap)
				.where(qYsyGrpMenuMap.ysyGrpMst.levelId.goe(levelId))
				.fetch();
		
		System.out.println();
	}
}
