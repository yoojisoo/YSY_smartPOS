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
import com.ysy.jwt.auth.dto.UserMngDto;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyUserMst;

@Service
public class YsyUserMngService {
	
	@PersistenceContext
	EntityManager em;
	
	/** 2022 07 07 mnew2m
	 * 사용하는 Q Class List */
	QYsyUserMst        qYsyUserMst = QYsyUserMst.ysyUserMst;
	QYsyGrpMenuMap qYsyGrpMenuMap  = QYsyGrpMenuMap.ysyGrpMenuMap;
	
	/** 유저 1명 조회 : userId의 해당 UserMst 테이블 조회 */
	@Transactional
	public UserMngDto getUser(String userId) {
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		YsyUserMst ysyUserMst = query.selectFrom(qYsyUserMst)
										   .where(qYsyUserMst.username.eq(userId))
										   .fetchOne();
		
		UserMngDto result = new UserMngDto(ysyUserMst);
		
		return result;
	}
	
	/** 유저 1명 조회 : userId의 연관 테이블 (grpMst, UserAddress) 조회 */
	@Transactional
	public UserMngDto getUserAddGrp(String userId) {
		JPAQueryFactory query = new JPAQueryFactory(em);
		
//		YsyUserMst ysyUserMst = query.selectFrom(qYsyUserMst)
//				.where(qYsyUserMst.username.eq(userId))
//				.fetchOne();
//		
//		UserDto result = new UserDto(ysyUserMst);
		
		return null;
	}
	
	/** 모든 유저 조회 : UserMst의 모든 정보를 size만큼 */
	@Transactional
	public List<UserMngDto> getUserList(int size) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		List<YsyUserMst> ysyUserMstList = query.selectFrom(qYsyUserMst)
											   .limit(size)
											   .fetch();
		
		List<UserMngDto> resultList = new ArrayList<UserMngDto>();
		
		for(YsyUserMst user : ysyUserMstList) {
			resultList.add(new UserMngDto(user));
		}
		
		return resultList;
		
		
//		List<Object[]> resultList = ysyUserRepository.getDefaultUserList();
//		
//		List<UserDto> userList = resultList.stream()
//										  .map(x -> UserDto.builder()
//												  .user_id((String)x[0])
//												  .user_phone((String)x[1])
//												  .user_nm((String)x[2])
//												  .reg_dt((String)x[3])
//												  .grp_id((String)x[6])
//										  		  .build()
//										  	   )
//										  .collect(Collectors.toList());
//		UserDto userDto = new UserDto();
//		userDto.setObj(resultList);
//		return userList;
	}
	
	@Transactional
	public ResponseDto<UserMngDto> getFilterUserList(String userId) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		/** 나의 정보 start */
		YsyUserMst myInfo = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.where(qYsyUserMst.username.eq(userId))
				.fetchOne();
		
		// 조회된 나의 정보에서 bizCd와 levelId를 미리 가져옴
		String bizCd = myInfo.getYsyGrpMst().getGrpPK().getBizCd();
		int  levelId = myInfo.getYsyGrpMst().getLevelId();
		/** 나의 정보 end */
		
		
		/** 나보다 낮은 등급의 사용자 정보 start */
		List<YsyUserMst> userList = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.where(qYsyUserMst.username.ne(userId)
					  ,qYsyUserMst.ysyGrpMst.grpPK.bizCd.eq(bizCd)
					  ,qYsyUserMst.ysyGrpMst.levelId.goe(levelId))
				.fetch();
		/** 나보다 낮은 등급의 사용자 정보 end */
		
		List<UserMngDto> resultList = new ArrayList<UserMngDto>();
		for(YsyUserMst user : userList) {
			resultList.add(new UserMngDto(user));
		}
		
		return new ResponseDto<UserMngDto>(resultList, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseDto<MenuDto> getUserMenuList(String userId) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		/** 해당 유저의 정보 start */
		YsyUserMst userInfo = query
				.selectFrom(qYsyUserMst)
				.where(qYsyUserMst.username.eq(userId))
				.fetchOne();
		
		int levelId = userInfo.getYsyGrpMst().getLevelId();
		/** 해당 유저의 정보 end */
		
		/** 해당 유저의 접근가능메뉴 정보 start */
		List<YsyGrpMenuMap> menuList = query
				.selectFrom(qYsyGrpMenuMap)
				.where(qYsyGrpMenuMap.ysyGrpMst.levelId.goe(levelId))
				.orderBy(qYsyGrpMenuMap.ysyMenuMst.menuSeq.asc())
				.fetch();
		/** 해당 유저의 접근가능메뉴 정보 end */
		
		List<MenuDto> resultList = new ArrayList<MenuDto>();
		for(YsyGrpMenuMap menu : menuList) {
			resultList.add(new MenuDto(menu));
		}
		
		return new ResponseDto<MenuDto>(resultList, HttpStatus.OK);
	}
	
	/** grid에서 user 삭제 */
//	public void delGridUserInfo(List<String> usernameList) {
//		for(String username : usernameList) {
//			ysyUserRepository.deleteById(username);
//		}
//	}
}
