package com.ysy;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.ysy.biz.entity.QStoreNotice;
import com.ysy.biz.entity.StoreNotice;
import com.ysy.jwt.auth.dto.UserInfoDto;
import com.ysy.jwt.auth.entity.QYsyBtnMst;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
import com.ysy.jwt.auth.entity.QYsyMenuExptMst;
import com.ysy.jwt.auth.entity.QYsyMenuMst;
import com.ysy.jwt.auth.entity.QYsyUserAddress;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.QYsyUserRTokenMap;
import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyMenuExptMst;
import com.ysy.jwt.auth.entity.YsyUserAddress;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.entity.YsyUserRTokenMap;
import com.ysy.jwt.auth.repository.YsyUserAddressRepository;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Jpa_1_N_test {

	@Autowired
	private YsyUserMstRepository ysyUserMstRepository;
	@Autowired
	private YsyUserAddressRepository ysyUserAddressRepository;
	
	@PersistenceContext
	EntityManager em; // 1
	
	
	@Test
	@Transactional
	public void getUserList() {
		String userId = "clubbboy@naver.com1";
		try {
			assertEquals(1,1);
			System.out.println("success==================================");
		} catch (Exception e) {
			System.out.println("exception==================================");
			e.printStackTrace();
		}
		 
		JPAQueryFactory           query = new JPAQueryFactory(em); // 2
		QStoreNotice       qStoreNotice = QStoreNotice.storeNotice;
		QYsyGrpMenuMap   qYsyGrpMenuMap = QYsyGrpMenuMap.ysyGrpMenuMap;
		QYsyUserMst         qYsyUserMst = QYsyUserMst.ysyUserMst;
		QYsyMenuMst         qYsyMenuMst = QYsyMenuMst.ysyMenuMst;
		QYsyGrpMst           qYsyGrpMst = QYsyGrpMst.ysyGrpMst;
		QYsyBtnMst           qYsyBtnMst = QYsyBtnMst.ysyBtnMst;
		QYsyMenuExptMst qYsyMenuExptMst = QYsyMenuExptMst.ysyMenuExptMst;
		QYsyUserAddress qYsyUserAddress = QYsyUserAddress.ysyUserAddress;
		QYsyUserRTokenMap qYsyUserRTokenMap = QYsyUserRTokenMap.ysyUserRTokenMap;
		
		
		YsyUserRTokenMap tokenMap = query
				.select(qYsyUserRTokenMap)
				.from(qYsyUserRTokenMap)
				.innerJoin(qYsyUserMst).fetchJoin()
				.on(qYsyUserRTokenMap.ysyUserMst.username.eq(qYsyUserMst.username))
				.where(qYsyUserRTokenMap.ysyUserMst.username.eq(userId))
				.limit(1)
				.fetchOne();
		
		List<StoreNotice> noticeList = query
				.select(qStoreNotice)
				.from(qStoreNotice)
				.where(qStoreNotice.ysyUserMst.username.eq(userId))
				.fetch();
		
		List<YsyUserAddress> addrList = query
				.select(qYsyUserAddress)
				.from(qYsyUserAddress)
				.innerJoin(qYsyUserAddress.ysyUserMst , qYsyUserMst)
				.fetchJoin()
				.where(qYsyUserAddress.ysyUserMst.username.eq(userId))
				.fetch();
		UserInfoDto dto1 = new UserInfoDto(addrList); 
		
		
		
		List<YsyUserMst> userAddressList = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.leftJoin(qYsyUserAddress)
				.on(qYsyUserMst.username.eq(qYsyUserAddress.ysyUserMst.username))
				.where(qYsyUserMst.username.eq(userId))
				.fetch();
		
		
		// 권한에 따른 level id
		YsyGrpMst grp_lvl = query
				.selectFrom(qYsyGrpMst)
				.innerJoin(qYsyGrpMst.ysyUserMst , qYsyUserMst)
				.fetchJoin()
				.where(qYsyUserMst.username.eq(userId))
				.fetchOne();
		System.out.println("grp_lvl.getLevelId() ===> "+grp_lvl.getLevelId());
		
		//권한에 따른 메뉴 리스트
		List<YsyGrpMenuMap> myMenuList = query
				.select(qYsyGrpMenuMap )
				.from(qYsyGrpMenuMap )
				.innerJoin(qYsyGrpMenuMap.ysyMenuMst , qYsyMenuMst)
				.fetchJoin()
				.innerJoin(qYsyGrpMenuMap.ysyGrpMst  , qYsyGrpMst )
				.fetchJoin()
				.where(qYsyGrpMenuMap.ysyGrpMst.levelId.goe(grp_lvl.getLevelId())
				      ,qYsyGrpMenuMap.ysyGrpMst.grpPK.bizCd.eq(grp_lvl.getYsyBizMst().getBizCd())
			    )
				.orderBy(qYsyMenuMst.menuSeq.asc())
				.fetch();

		//예외 메뉴 select
		List<YsyMenuExptMst> exptMenuList = query
				.select(qYsyMenuExptMst)
				.from(qYsyMenuExptMst)
				.innerJoin(qYsyMenuExptMst.ysyMenuMst, qYsyMenuMst).fetchJoin()
				.innerJoin(qYsyMenuExptMst.ysyUserMst, qYsyUserMst).fetchJoin()
				.where(qYsyMenuExptMst.ysyUserMst.username.eq(userId)
				  .and(qYsyMenuExptMst.isView.eq("N"))
				)
				.fetch();
		
		//user 1명 조회
		List<YsyUserMst> userResult = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.leftJoin(qYsyUserAddress)
				.on(qYsyUserMst.username.eq(qYsyUserAddress.ysyUserMst.username))
				.fetchJoin()
				.where(qYsyUserMst.username.eq(userId))
				.fetch();
		
//		List<UserMngDto2> userMngList = 
//				userResult.stream()
//					.map(x-> new UserMngDto2(x.getUsername() , x.getAddressList()))
//					.collect(Collectors.toList());
//		UserMngDto2 userMngDto2 = userMngList.get(0);
		
		
		YsyUserMst user = ysyUserMstRepository.findById(userId)
				.orElseThrow(()->  new IllegalArgumentException("except test"));//
		
		List<YsyUserAddress> tmpAddrList = ysyUserAddressRepository.findByYsyUserMst(user);
		UserInfoDto dto2 = new UserInfoDto(tmpAddrList);
		
		System.out.println("");
	}
}
