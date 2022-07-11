package com.ysy.jwt.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.biz.dto.ResponseDto;
import com.ysy.jwt.auth.dto.MenuDto;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
import com.ysy.jwt.auth.entity.QYsyMenuMst;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyGrpMst;

@Service
//@AllArgsConstructor
public class YsyMenuMstService {
	
	@PersistenceContext
	EntityManager em;
	
	/** 22-07-05 mnew2m
	 * 사용하는 Q Class */
	QYsyGrpMenuMap qYsyGrpMenuMap = QYsyGrpMenuMap.ysyGrpMenuMap;
	QYsyUserMst       qYsyUserMst = QYsyUserMst.ysyUserMst;
	QYsyMenuMst       qYsyMenuMst = QYsyMenuMst.ysyMenuMst;
	QYsyGrpMst         qYsyGrpMst = QYsyGrpMst.ysyGrpMst;
	
	/** 22-07-05 mnew2m
	 * 로그인 된 아이디가 없을 때 */
	@Transactional
	public ResponseDto<MenuDto> findDefaultMenuList() {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		/** 로그인하지 않았을때 디폴트 bizCd 0001 ★★★★★ 임시 ★★★★★
		 * 해당 bizCd Grp중에 가장 숫자가 큰 그룹(DEFAULT_USER) 1건만 가져옴 */
		// default grp data setting start
		String defaultBizCd = "0001";
		YsyGrpMst defaultGrp = query
				.select(qYsyGrpMst)
				.from(qYsyGrpMst)
				.where(qYsyGrpMst.ysyBizMst.bizCd.eq(defaultBizCd))
				.orderBy(qYsyGrpMst.levelId.desc())
				.limit(1)
				.fetchOne();
		// default grp data setting end
		
		// default user가 사용 가능한 menu list 가져오는 쿼리
		List<YsyGrpMenuMap> menuList = query
				.select(qYsyGrpMenuMap)
				.from(qYsyGrpMenuMap)
				.innerJoin(qYsyMenuMst).fetchJoin()
				.on(qYsyGrpMenuMap.ysyMenuMst.menuId.eq(qYsyMenuMst.menuId))
				.where(qYsyGrpMenuMap.ysyGrpMst.levelId.goe(defaultGrp.getLevelId())
					 , qYsyGrpMenuMap.ysyGrpMst.grpPK.bizCd.eq(defaultGrp.getYsyBizMst().getBizCd()))
				.fetch();
		
		List<MenuDto> resultList = new ArrayList<MenuDto>();
		for(YsyGrpMenuMap menu : menuList) {
			resultList.add(new MenuDto(menu));
		}
		
		return new ResponseDto<MenuDto>(resultList, HttpStatus.OK);
	}
	
	/** 22-07-05 mnew2m
	 * 로그인 된 아이디가 있을 때 */
	@Transactional
	public ResponseDto<MenuDto> findMenuList(String userId) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		// 해당 아이디의 grp 정보 가져오는 쿼리
		YsyGrpMst grp_lvl = query
				.select(qYsyGrpMst)
				.from(qYsyGrpMst)
				.innerJoin(qYsyUserMst)
				.on(qYsyGrpMst.ysyBizMst.bizCd.eq(qYsyUserMst.ysyGrpMst.grpPK.bizCd)
				   ,qYsyGrpMst.grpPK.grpId.eq(qYsyUserMst.ysyGrpMst.grpPK.grpId))
				.where(qYsyUserMst.username.eq(userId))
				.fetchOne();
		
		// 해당 아이디가 사용 가능한 menu list 가져오는 쿼리
		List<YsyGrpMenuMap> menuList = query
				.select(qYsyGrpMenuMap)
				.from(qYsyGrpMenuMap)
				.innerJoin(qYsyGrpMenuMap.ysyGrpMst, qYsyGrpMst).fetchJoin()
				.innerJoin(qYsyGrpMenuMap.ysyMenuMst, qYsyMenuMst).fetchJoin()
				.where(qYsyGrpMenuMap.ysyMenuMst.menuId.contains("menu-")
					 , qYsyGrpMenuMap.ysyGrpMst.levelId.goe(grp_lvl.getLevelId())
					 , qYsyGrpMenuMap.ysyGrpMst.grpPK.bizCd.eq(grp_lvl.getYsyBizMst().getBizCd()))
				.fetch();
		
		List<MenuDto> resultList = new ArrayList<MenuDto>();
		for(YsyGrpMenuMap menu : menuList) {
			resultList.add(new MenuDto(menu));
		}
		
		return new ResponseDto<MenuDto>(resultList, HttpStatus.OK);
	}
}
