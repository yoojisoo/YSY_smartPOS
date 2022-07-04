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

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.biz.entity.QStoreNotice;
import com.ysy.biz.entity.StoreNotice;
import com.ysy.jwt.auth.dto.UserInfoDto;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
import com.ysy.jwt.auth.entity.QYsyMenuMst;
import com.ysy.jwt.auth.entity.QYsyUserAddress;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyUserAddress;
import com.ysy.jwt.auth.entity.YsyUserMst;
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
		
		
		JPAQueryFactory query = new JPAQueryFactory(em); // 2
		QStoreNotice qStoreNotice = QStoreNotice.storeNotice;
		List<StoreNotice> result = query
				.select(qStoreNotice)
				.from(qStoreNotice)
				.where(qStoreNotice.ysyUserMst.username.eq("clubbboy@naver.com"))
				.fetch();
		
		QYsyGrpMenuMap qYsyGrpMenuMap = QYsyGrpMenuMap.ysyGrpMenuMap;
		QYsyUserMst qYsyUserMst = QYsyUserMst.ysyUserMst;
		QYsyMenuMst qYsyMenuMst = QYsyMenuMst.ysyMenuMst;
		QYsyGrpMst qYsyGrpMst = QYsyGrpMst.ysyGrpMst;
		
		// 권한에 따른 level id
		YsyGrpMst grp_lvl = query
				.selectFrom(qYsyGrpMst)
				.innerJoin(qYsyUserMst)
				.on(qYsyGrpMst.ysyBizMst.bizCd.eq(qYsyUserMst.ysyGrpMst.grpPK.bizCd),
					qYsyGrpMst.grpPK.grpId.eq(qYsyUserMst.ysyGrpMst.grpPK.grpId)	
				)
				.where(qYsyUserMst.username.eq("clubbboy@naver.com"))
				.fetchOne();
		System.out.println("grp_lvl.getLevelId() ===> "+grp_lvl.getLevelId());
		
		//권한에 따른 메뉴 리스트
		List<YsyGrpMenuMap> myMenuList = query
				.selectFrom(qYsyGrpMenuMap )
				.innerJoin(qYsyMenuMst)
				.on(qYsyGrpMenuMap.ysyMenuMst.menuId.eq(qYsyMenuMst.menuId))
				.where(qYsyGrpMenuMap.ysyGrpMst.levelId.goe(grp_lvl.getLevelId())
				  .and(qYsyGrpMenuMap.ysyGrpMst.grpPK.bizCd.eq(grp_lvl.getYsyBizMst().getBizCd()))
			    )
//				.innerJoin(qYsyGrpMst,qYsyGrpMenuMap.ysyGrpMst)
//				.innerJoin(qYsyUserMst.ysyGrpMst,qYsyGrpMst)
//				.where(qYsyUserMst.username.eq("clubbboy@naver.com"))
				.fetch();

		
		QYsyUserAddress qYsyUserAddress = QYsyUserAddress.ysyUserAddress;
		
		List<YsyUserAddress> result2 = query
				.select(qYsyUserAddress)
				.from(qYsyUserAddress)
				.where(qYsyUserAddress.ysyUserMst.username.eq("clubbboy@naver.com"))
				.fetch();
//		List<YsyUserAddress> listt = ysyUserAddressRepository.getUserInfoJPQL("clubbboy@naver.com");
		UserInfoDto dto1 = new UserInfoDto(result2); 
		
		
		
//		List<UserInfoDto> result123 = query
//				.select(Projections.bean( UserInfoDto.class,
//						qYsyUserAddress.ysyUserMst.username,
//						qYsyUserAddress.ysyUserMst.name,
//						qYsyUserAddress.ysyUserMst.oAuthPath,
//						qYsyUserAddress.ysyUserMst.ysyGrpMst.grpPK.grpId,
//						qYsyUserAddress.ysyUserMst.ysyGrpMst.grpPK.bizCd,
//						qYsyUserAddress.addrType,
//						qYsyUserAddress.addrZipCode,
//						qYsyUserAddress.addrCity,
//						qYsyUserAddress.addrDetail,
//						qYsyUserAddress.addrEtc,
//						qYsyUserAddress.phone1,
//						qYsyUserAddress.phone2,
//						qYsyUserAddress.regId,
//						qYsyUserAddress.regDt,
//						qYsyUserAddress.modId,
//						qYsyUserAddress.modDt
//						)
//				)
//				.from(qYsyUserAddress)
//				.where(qYsyUserAddress.ysyUserMst.username.eq("clubbboy@naver.com"))
//				.fetch();
		
		
		
		List<YsyUserAddress> resultTuple = query
				.select(qYsyUserAddress
//						qYsyUserAddress.phone1,
//						qYsyUserAddress.phone2,
//						qYsyUserAddress.regId,
//						qYsyUserAddress.regDt,
//						qYsyUserAddress.modId,
//						qYsyUserAddress.modDt
				)
				.from(qYsyUserAddress)
				.innerJoin(qYsyUserMst)
				.on(qYsyUserAddress.ysyUserMst.username.eq(qYsyUserMst.username) )
				.where(qYsyUserAddress.ysyUserMst.username.eq("clubbboy@naver.com"))
				.fetch();
		
		
		
		
		YsyUserMst user = ysyUserMstRepository.findById("clubbboy@naver.com")
				.orElseThrow(()->  new IllegalArgumentException("except test"));//
		
		List<YsyUserAddress> tmpAddrList = ysyUserAddressRepository.findByYsyUserMst(user);
		UserInfoDto dto2 = new UserInfoDto(tmpAddrList);
		
		System.out.println("");
	}
}
