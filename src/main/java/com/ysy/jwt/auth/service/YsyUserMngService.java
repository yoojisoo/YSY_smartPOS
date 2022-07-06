package com.ysy.jwt.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.dto.UserMngDto;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyUserMst;

@Service
public class YsyUserMngService {
	
	@PersistenceContext
	EntityManager em;
	
	
	/** 유저 1명 조회 : userId의 해당 UserMst 테이블 조회 */
	@Transactional
	public UserMngDto getUser(String userId) {
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		QYsyUserMst qYsyUserMst = QYsyUserMst.ysyUserMst;
		
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
		
		QYsyUserMst qYsyUserMst = QYsyUserMst.ysyUserMst;
		
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
		
		QYsyUserMst qYsyUserMst = QYsyUserMst.ysyUserMst;
		
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
	
	/** grid에서 user 삭제 */
//	public void delGridUserInfo(List<String> usernameList) {
//		for(String username : usernameList) {
//			ysyUserRepository.deleteById(username);
//		}
//	}
}
