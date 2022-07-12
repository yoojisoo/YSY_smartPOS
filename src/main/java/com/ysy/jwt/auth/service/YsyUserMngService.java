package com.ysy.jwt.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.MenuDto;
import com.ysy.jwt.auth.dto.ModUserDto;
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.dto.UserMngDto;
import com.ysy.jwt.auth.entity.QYsyBizMst;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyUserAddress;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyUserMst;

@Service
public class YsyUserMngService {

	@Autowired
	private YsyUtil ysyUtil;
	
	@PersistenceContext
	EntityManager em;
	
	/** 2022 07 07 mnew2m
	 * 사용하는 Q Class List */
	QYsyUserMst     qYsyUserMst 	= QYsyUserMst.ysyUserMst;
	QYsyGrpMenuMap 	qYsyGrpMenuMap  = QYsyGrpMenuMap.ysyGrpMenuMap;
	QYsyUserAddress qYsyUserAddress = QYsyUserAddress.ysyUserAddress;	
	QYsyBizMst		qYsyBizMst 		= QYsyBizMst.ysyBizMst;	
	
	
	/** 유저 1명 조회 : userId의 연관 테이블 (UserAddress) 조회 */
	@Transactional
	public ResponseAuthDto<UserMngDto> getUserDetail(String userId) {
//		ResponseAuthDto<UserMngDto>
		/**
		 * 문제1. 유저 정보 1개에 어드레스 정보 여러개 : 구조 문제(무한순환)
		 *		  ex) user.get(0).ysyAddr.get(0) 과 user.get(1).ysyAddr.get(0) 의 내용 같음 : 중복
		 *		  dto로 변환하여 조회로 인한 순환 방지 & @JsonBackReference로 순환 방지 & get(0)만 사용하여 중복 해결
		 * 문제2. addrList를 dto List로 보내도 jackson 에러 -> addrList 형태로 내보냄
		 * */
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		List<YsyUserMst> userInfoList = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.leftJoin(qYsyBizMst)
				.on(qYsyUserMst.ysyGrpMst.ysyBizMst.bizNm.eq(qYsyBizMst.bizNm))
				.leftJoin(qYsyUserAddress)
				.on(qYsyUserMst.username.eq(qYsyUserAddress.ysyUserMst.username))
				.fetchJoin()
				.where(qYsyUserMst.username.eq(userId))
				.fetch();
		
		/** dto 변환 방식 1 - 0번째와 1번째의 데이터가 중복되므로 0번째만 사용 */
		UserMngDto userToDto = new UserMngDto(userInfoList.get(0), userInfoList.get(0).getAddressList());
		
//		/** dto 변환 방식 2 */
//		List<UserMngDto> tmpList = userInfoList.stream()
//				.map( x-> new UserMngDto(
//											x.getUsername(), x.getName(), x.getRegDt(), x.getOAuthPath(),
//											x.getYsyGrpMst().getYsyBizMst().getBizNm(), x.getAddressList())
//										)
//				.collect(Collectors.toList());
//		UserMngDto result = tmpList.get(0);
		
		return new ResponseAuthDto<UserMngDto>(userToDto, HttpStatus.OK); 
	}
	
	/** 모든 유저 조회 : UserMst의 모든 정보를 size만큼 */
	@Transactional
	public ResponseAuthDto<UserMngDto> getUserList(int size) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		List<UserMngDto> userList = query
				.selectFrom(qYsyUserMst)
				.limit(size)
				.fetch()
				.stream()
				.map(x->new UserMngDto(x))
				.collect(Collectors.toList());
		
		return new ResponseAuthDto<UserMngDto>(userList, HttpStatus.OK);
	}
	
	
	@Transactional
	public ResponseAuthDto<UserMngDto> getFilterUserList(String userId) {
		
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
		
		return new ResponseAuthDto<UserMngDto>(resultList, HttpStatus.OK);
	}
	
	@Transactional
	public ResponseAuthDto<MenuDto> getUserMenuList(String userId) {
		
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
		
		return new ResponseAuthDto<MenuDto>(resultList, HttpStatus.OK);
	}
	
	
	@Transactional
	public String modUserInfo(ModUserDto modUserDto) {
		try {
			if(!ysyUtil.isNullAndEmpty(modUserDto.getUsername())
			&& !ysyUtil.isNullAndEmpty(modUserDto.getName())
			&& !ysyUtil.isNullAndEmpty(modUserDto.getPassword())) 
			{
//				YsyUserMst orgYsyUser = ysyUserRepository.findById(modUserDto.getUsername())
//						.orElseThrow(()->  new IllegalArgumentException("id가 존재하지 않습니다.") );
//				YsyUserMst ysyUser = YsyUserMst.builder()
//						.username(modUserDto.getUsername())
//						.password(bCryptPasswordEncoder.encode(modUserDto.getPassword()))
//						.name(modUserDto.getName())
//						.ysyGrpMst(orgYsyUser.getYsyGrpMst())
//						.oAuthPath(orgYsyUser.getOAuthPath())
//						.build();
//				ysyUserRepository.save(ysyUser);
				
				JPAQueryFactory query = new JPAQueryFactory(em);
				
				query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.fetchOne();
				long execute = query
				        .update(qYsyUserMst)
				        .set(qYsyUserMst.name, modUserDto.getName())
				        .where(qYsyUserMst.username.eq(modUserDto.getUsername()))
				        .execute();
				if(execute > 0)
					return "ok";
				else return "fail";
			}
			else 
			{
				return  "[YsyUserMstService/modUserInfo] ModUserDto 정보 error !!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "[YsyUserMstService/modUserInfo] catch error !!";
		}
	}
	
	
	/** grid에서 user 삭제 */
//	public void delGridUserInfo(List<String> usernameList) {
//		for(String username : usernameList) {
//			ysyUserRepository.deleteById(username);
//		}
//	}
	
	
}
