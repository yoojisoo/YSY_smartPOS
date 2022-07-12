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
import com.ysy.biz.dto.ResponseDto;
import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.MenuDto;
import com.ysy.jwt.auth.dto.ModUserDto;
import com.ysy.jwt.auth.dto.UserMngDto;
import com.ysy.jwt.auth.dto.UserMngDto2;
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
	QYsyUserMst        qYsyUserMst = QYsyUserMst.ysyUserMst;
	QYsyGrpMenuMap qYsyGrpMenuMap  = QYsyGrpMenuMap.ysyGrpMenuMap;
	QYsyUserAddress qYsyUserAddress = QYsyUserAddress.ysyUserAddress;	
	
	
	/** 유저 1명 조회 : userId의 연관 테이블 (UserAddress) 조회 */
	@Transactional
	public UserMngDto getUserDetail(String userId) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		List<YsyUserMst> userInfoList = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.leftJoin(qYsyUserAddress)
				.on(qYsyUserMst.username.eq(qYsyUserAddress.ysyUserMst.username))
				.fetchJoin()
				.where(qYsyUserMst.username.eq(userId))
				.fetch();
		
		/** 0번째와 1번째의 데이터가 중복되므로 0번째만 사용 */
//		UserMngDto result = new UserMngDto(userInfoList.get(0), userInfoList.get(0).getAddressList());
		
		
		List<UserMngDto> tmpList = userInfoList.stream()
				.map( x-> new UserMngDto(
											x.getUsername(), x.getName(), x.getRegDt(), x.getOAuthPath(),
											x.getYsyGrpMst().getYsyBizMst().getBizNm(), x.getAddressList())
										)
				.collect(Collectors.toList());
	
		UserMngDto result = tmpList.get(0);
		
		
//		System.out.println("변환값 ---------------------> " + result.getUserId() + "주소 = " + result.getAddrList().get(0).getAddrCity() + " 뿅");
		/** user정보 기준으로 address를 조회 시 구조적 문제 발생
		 * user table에 address가 List형태로 존재
		 * ex -> user.get(0).ysyAddr.get(0) 과 user.get(1).ysyAddr.get(0) 의 내용 같음 : 중복
		 * dto를 이용하여 바로 변환.
		 * 이 경우 resultList의 get(0)만 가져가기로 함. DTO 처리 나중에 사용할 수 있기에 코드 주석으로 보존.
		 *  */
//		List<UserMngDto> resultList = query
//				.select(Projections.constructor(UserMngDto.class,
//						qYsyUserMst.username,
//						qYsyUserMst.name,
//						qYsyUserMst.regDt,
//						qYsyUserMst.oAuthPath,
//						qYsyUserMst.ysyGrpMst.grpPK.bizCd,
//						qYsyUserAddress.addrType,
//						qYsyUserAddress.addrZipCode,
//						qYsyUserAddress.addrCity,
//						qYsyUserAddress.addrDetail,
//						qYsyUserAddress.addrEtc,
//						qYsyUserAddress.phone1,
//						qYsyUserAddress.phone2
//						)
//				)
//				.from(qYsyUserMst)
//				.innerJoin(qYsyGrpMst).fetchJoin()
//				.on(qYsyUserMst.ysyGrpMst.grpPK.bizCd.eq(qYsyGrpMst.grpPK.bizCd)
//				   ,qYsyUserMst.ysyGrpMst.grpPK.grpId.eq(qYsyGrpMst.grpPK.grpId))
//				.leftJoin(qYsyUserAddress).fetchJoin()
//				.on(qYsyUserMst.username.eq(qYsyUserAddress.ysyUserMst.username))
//				
//				.where(qYsyUserMst.username.eq(userId))
//				.fetch();
		
		return result; 
	}
	
	/** 모든 유저 조회 : UserMst의 모든 정보를 size만큼 */
	@Transactional
	public List<UserMngDto> getUserList(int size) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		return query
				.selectFrom(qYsyUserMst)
				.limit(size)
				.fetch()
				.stream()
				.map(x->new UserMngDto(x))
				.collect(Collectors.toList());
		
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
