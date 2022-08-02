package com.ysy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.common.SysEnum;
import com.ysy.jwt.auth.common.SysEnum.enumGrps;
import com.ysy.jwt.auth.entity.QYsyBizMst;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
import com.ysy.jwt.auth.entity.QYsyMenuMst;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.entity.YsyGrpMst;
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
	QYsyBizMst          qYsyBizMst = QYsyBizMst.ysyBizMst;
	QYsyMenuMst        qYsyMenuMst = QYsyMenuMst.ysyMenuMst;
	
	@Test
	@Transactional
	public void getUserList() {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		String userId = "s_plus7@naver.com";
		String bizCd = "0001";
		enumGrps grpId = SysEnum.enumGrps.ROLE_TEMP_USER;
		
		YsyUserMst user = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.innerJoin(qYsyUserMst.ysyGrpMst, qYsyGrpMst).fetchJoin()
				.where(qYsyUserMst.username.eq("mnew2m@gmail.com"))
				.fetchOne();
		
//		String grpId = user.getYsyGrpMst().getGrpPK().getGrpId().toString();
		
		System.out.println();
		
		YsyGrpMst ysyGrpMst = query
				.select(qYsyGrpMst)
				.from(qYsyGrpMst)
				.innerJoin(qYsyGrpMst.ysyBizMst, qYsyBizMst).fetchJoin()
				.where(qYsyGrpMst.ysyBizMst.bizCd.eq(bizCd)
					 , qYsyGrpMst.grpPK.grpId.eq(grpId))
				.fetchOne();
		
		YsyBizMst ysyBizMst = query
				.select(qYsyBizMst)
				.from(qYsyBizMst)
				.where(qYsyBizMst.bizCd.eq("0001"))
				.fetchOne();
		
//		YsyUserMst isUser = query
//				.select(qYsyUserMst)
//				.from(qYsyUserMst)
//				.where(qYsyUserMst.username.eq(userId))
//				.fetchOne();
//		
//		String defaultBizCd = "0001";
//		YsyGrpMst defaultGrp = query
//				.select(qYsyGrpMst)
//				.from(qYsyGrpMst)
//				.innerJoin(qYsyGrpMst.ysyBizMst, qYsyBizMst).fetchJoin()
//				.where(qYsyGrpMst.ysyBizMst.delYn.eq("N")
//					 , qYsyGrpMst.ysyBizMst.useYn.eq("Y")
//					 , qYsyGrpMst.ysyBizMst.bizCd.eq(defaultBizCd))
//				.orderBy(qYsyGrpMst.levelId.desc())
//				.limit(1)
//				.fetchOne();
//		
//		List<YsyGrpMenuMap> menuList = query
//				.select(qYsyGrpMenuMap)
//				.from(qYsyGrpMenuMap)
//				.innerJoin(qYsyGrpMenuMap.ysyGrpMst, qYsyGrpMst).fetchJoin()
//				.innerJoin(qYsyGrpMenuMap.ysyMenuMst, qYsyMenuMst).fetchJoin()
//				.where(qYsyGrpMenuMap.ysyMenuMst.menuId.eq(qYsyMenuMst.menuId)
//					 , qYsyGrpMenuMap.ysyGrpMst.levelId.goe(defaultGrp.getLevelId())
//					 , qYsyGrpMenuMap.ysyGrpMst.grpPK.bizCd.eq(defaultGrp.getYsyBizMst().getBizCd()))
//				.fetch();
//		
//		/** 나의 정보 start */
//		YsyUserMst myInfo = query
//				.selectFrom(qYsyUserMst)
//				.where(qYsyUserMst.username.eq(userId))
//				.fetchOne();
//		
//		String bizCd = myInfo.getYsyGrpMst().getGrpPK().getBizCd();
//		int  levelId = myInfo.getYsyGrpMst().getLevelId();
//		
//		System.out.println();
//		/** 나의 정보 end */
//		
//		
//		/** 나보다 낮은 등급의 사용자 정보 start */
//		List<YsyUserMst> userList = query
//				.selectFrom(qYsyUserMst)
//				.where(qYsyUserMst.username.ne(userId)
//						.and(qYsyUserMst.ysyGrpMst.grpPK.bizCd.eq(bizCd))
//						.and(qYsyUserMst.ysyGrpMst.levelId.goe(levelId)))
//				.fetch();
//		
//		System.out.println();
//		/** 나보다 낮은 등급의 사용자 정보 end */
		
//		YsyUserMst userInfo = query
//				.selectFrom(qYsyUserMst)
//				.where(qYsyUserMst.username.eq(userId))
//				.fetchOne();
//		
//		int levelId = userInfo.getYsyGrpMst().getLevelId();
//		
//		List<YsyGrpMenuMap> menuList = query
//				.selectFrom(qYsyGrpMenuMap)
//				.where(qYsyGrpMenuMap.ysyGrpMst.levelId.goe(levelId))
//				.orderBy(qYsyGrpMenuMap.ysyMenuMst.menuSeq.asc())
//				.fetch();
//		
//		System.out.println();
	}
}
