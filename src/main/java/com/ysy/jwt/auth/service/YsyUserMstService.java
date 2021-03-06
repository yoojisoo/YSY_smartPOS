package com.ysy.jwt.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.common.SysEnum;
import com.ysy.jwt.auth.common.YsyUtil;
import com.ysy.jwt.auth.common.SysEnum.enumGrps;
import com.ysy.jwt.auth.dto.JoinDto;
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.dto.UserDto;
import com.ysy.jwt.auth.entity.QYsyBizMst;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
import com.ysy.jwt.auth.entity.QYsyUserAddress;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.repository.YsyBizMstRepository;
import com.ysy.jwt.auth.repository.YsyGrpMstRepository;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;

@Service
public class YsyUserMstService {
	private static final int UserDto = 0;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private YsyUserMstRepository ysyUserRepository;
	@Autowired
	YsyBizMstService ysyBizService;
	@Autowired
	YsyGrpMstService ysyGrpService;
	@Autowired
	YsyGrpMstRepository ysyGrpRepository;
	@Autowired
	YsyBizMstRepository ysyBizRepository;
	@Autowired
	YsyUtil util;
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	JPAQueryFactory query = new JPAQueryFactory(em);

	
	/** 2022 07 07 mnew2m
	 * ???????????? Q Class List */
	QYsyUserMst     qYsyUserMst 	= QYsyUserMst.ysyUserMst;
	QYsyGrpMst     qYsyGrpMst 	= QYsyGrpMst.ysyGrpMst;
	QYsyGrpMenuMap 	qYsyGrpMenuMap  = QYsyGrpMenuMap.ysyGrpMenuMap;
	QYsyUserAddress qYsyUserAddress = QYsyUserAddress.ysyUserAddress;	
	QYsyBizMst		qYsyBizMst 		= QYsyBizMst.ysyBizMst;	
	
	/** user ?????? */
	@Transactional
	public ResponseAuthDto<String> signUp(JoinDto joinDto) {
		
		ResponseAuthDto<String> resDto = new ResponseAuthDto<String>();
		
		try 
		{
			if (!util.isNullAndEmpty(joinDto.getBizCd())
			 && !util.isNullAndEmpty(joinDto.getUsername())
			 && !util.isNullAndEmpty(joinDto.getPassword())
			 && !util.isNullAndEmpty(joinDto.getName())) 
			{
				
				/** 0. ?????? ?????? ?????? (???????????? ????????? return / ???????????? ????????? ?????? ??????) */
				if (isUser(joinDto.getUsername())) {
					YsyUserMst userInfo = userInfo(joinDto.getUsername());
					String orgPath     = userInfo.getOAuthPath() == null || userInfo.getOAuthPath().equals("") ? "?????????" : userInfo.getOAuthPath();
					String joinDtoPath = joinDto.getOAuthPath()  == null || joinDto.getOAuthPath().equals("")  ? "?????????" : joinDto.getOAuthPath();
					
					if(orgPath.equals(joinDtoPath)) 
					{
						resDto.setMsg("ok");
						resDto.setStatus(HttpStatus.OK);
						return resDto;
					}
					
					String errorMsg =  "error : \n"
							         + "["+joinDto.getUsername() +"] ?????? ????????? " + orgPath + "??? ?????????????????? , "
							         + "???????????? " + joinDto.getOAuthPath() + "??? ??????????????????.\n"
					                 + orgPath +"??? ????????? ????????????!!";
					resDto.setMsg(errorMsg);
					resDto.setStatus(HttpStatus.BAD_REQUEST);
					return resDto;
				}
				
				// bizCd??? Default Group ID ??????
				String bizCd = joinDto.getBizCd();
				enumGrps grpId = SysEnum.enumGrps.ROLE_TEMP_USER;
				List<enumGrps> enumTemp = Stream.of(SysEnum.enumGrps.values()).collect(Collectors.toList());
				int enumRoleNum;
				
				YsyBizMst ysyBizMst = new YsyBizMst();
				YsyGrpMst ysyGrpMst = new YsyGrpMst();
				
				/** 1. bizCd ?????? ?????? */
				if (!ysyBizService.isBizCd(bizCd)) {
					enumRoleNum = 1;
					
					/** ???????????? ?????? -> bizCd??? ???????????? ????????? Default grpId??? ?????? ????????? ??? ??? ?????? ???????????? */
					ysyBizMst = YsyBizMst.builder().bizCd(bizCd).bizNm("").useYn("Y").delYn("N").build(); // biz ??????
					ysyBizRepository.save(ysyBizMst);
					
					for(enumGrps enumRole : enumTemp) {
						GrpPK grpPK = new GrpPK(enumRole, bizCd); // grp ????????? ??? Pk ????????? ?????????
						ysyGrpMst = YsyGrpMst.builder()
								.grpPK(grpPK)
								.ysyBizMst(ysyBizMst)
								.grpNm("")
								.useYn("Y")
								.levelId(enumRoleNum)
								.build(); // grp ??????
						
						ysyGrpRepository.save(ysyGrpMst);
						enumRoleNum++;
					}
					
					if (!ysyBizService.createBiz(ysyBizMst)) {
						resDto.setMsg("??????????????? ????????????.");
						resDto.setStatus(HttpStatus.BAD_REQUEST);
						return resDto;//"??????????????? ????????????.";
					}
				}
				
				/** ????????? -> bizCd??? ???????????? Default grpId??? ?????? ????????? Group ????????? ????????? */
				ysyGrpMst = query
						.select(qYsyGrpMst)
						.from(qYsyGrpMst)
						.innerJoin(qYsyGrpMst.ysyBizMst, qYsyBizMst).fetchJoin()
						.where(qYsyGrpMst.ysyBizMst.bizCd.eq(bizCd)
							 , qYsyGrpMst.grpPK.grpId.eq(grpId))
						.fetchOne();
				
				/** 2. ?????? ?????? ????????? YsyUserMst?????? ????????? ?????? ????????? */
				YsyUserMst ysyUser = YsyUserMst.builder()
						.username(joinDto.getUsername())
						.password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
						.name(joinDto.getName())
						.ysyGrpMst(ysyGrpMst)
						.oAuthPath(joinDto.getOAuthPath())
						.build();

				ysyUserRepository.save(ysyUser);
				resDto.setMsg("ok");
				resDto.setStatus(HttpStatus.OK);
				return resDto;//"ok";
			} else {
				resDto.setMsg("error : Id or password or name is empty!");
				resDto.setStatus(HttpStatus.BAD_REQUEST);
				return resDto;//"error : Id or password or name is empty!";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			resDto.setMsg("error exception");
			resDto.setStatus(HttpStatus.BAD_REQUEST);
			return resDto;//"error : user register error!";
		}
		
	}
	
//	public String modUserInfo(ModUserDto modUserDto) {
//		try {
//			if(!util.isNullAndEmpty(modUserDto.getUsername())
//			&& !util.isNullAndEmpty(modUserDto.getName())
//			&& !util.isNullAndEmpty(modUserDto.getPassword())) 
//			{
//				YsyUserMst orgYsyUser = ysyUserRepository.findById(modUserDto.getUsername())
//						.orElseThrow(()->  new IllegalArgumentException("id??? ???????????? ????????????.") );
//				YsyUserMst ysyUser = YsyUserMst.builder()
//						.username(modUserDto.getUsername())
//						.password(bCryptPasswordEncoder.encode(modUserDto.getPassword()))
//						.name(modUserDto.getName())
//						.ysyGrpMst(orgYsyUser.getYsyGrpMst())
//						.oAuthPath(orgYsyUser.getOAuthPath())
//						.build();
//				ysyUserRepository.save(ysyUser);
//				
//				return "ok";
//			}
//			else 
//			{
//				return  "[YsyUserMstService/modUserInfo] ModUserDto ?????? error !!";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "[YsyUserMstService/modUserInfo] catch error !!";
//		}
//	}
	
	/** ID??? ???????????? userInfo return */
	public YsyUserMst userInfo(String username) {
		
		
		YsyUserMst user = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.where(qYsyUserMst.username.eq(username))
				.fetchOne();
		
		return user;
	}
	
	/** user ???????????? ?????? ?????? : true */
	public boolean isUser(String username) {
		
		
		YsyUserMst user = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.where(qYsyUserMst.username.eq(username))
				.fetchOne();
		
		if(user == null)
			return false;
		return true;
	}
	
	/** isAdmin ?????? : admin?????? true */
	public boolean isAdmin(String username) {
		
		
		YsyUserMst user = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.innerJoin(qYsyUserMst.ysyGrpMst, qYsyGrpMst).fetchJoin()
				.where(qYsyUserMst.username.eq(username))
				.fetchOne();
		
		String grpId = user.getYsyGrpMst().getGrpPK().getGrpId().toString();
		
		if(grpId.contains("ADMIN") || grpId.contains("MANAGER"))
			return true;
		return false;
	}
	
	public ResponseAuthDto<UserDto> getUserListCondition(UserDto condition) {
		
		List<YsyUserMst> userInfoList = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.leftJoin(qYsyGrpMst)
				.on(qYsyUserMst.ysyGrpMst.grpPK.bizCd.eq(qYsyGrpMst.grpPK.bizCd)
				   ,qYsyUserMst.ysyGrpMst.grpPK.grpId.eq(qYsyGrpMst.grpPK.grpId))
				.fetchJoin()
				.leftJoin(qYsyBizMst)
				.on(qYsyGrpMst.grpPK.bizCd.eq(qYsyBizMst.bizCd))
				.fetchJoin()
//				.leftJoin(qYsyUserAddress)
//				.on(qYsyUserMst.username.eq(qYsyUserAddress.ysyUserMst.username))
//				.fetchJoin()
				.where(
						userIdEq(condition.getUserId()),
						userNmEq(condition.getUserNm()),
						isEmailAuthEq(condition.getOauthPath()),
						oauthPathEq(condition.getOauthPath()),
						useYnEq(condition.getUseYn()),
						bizCdEq(condition.getBizCd()),
						bizNmEq(condition.getBizNm()),
						grpIdEq(condition.getGrpId()),
						grpNmEq(condition.getGrpNm())
						)
				.fetch();
		
		List<UserDto> resultUserList = new ArrayList<UserDto>();
		
		for(YsyUserMst userInfo : userInfoList) {
			resultUserList.add( new UserDto(userInfo) );
		}
//		
		return new ResponseAuthDto<UserDto>(resultUserList, HttpStatus.OK); 
	}
	
	/** ?????? ?????? where??? - null return ??? ?????? ?????? X */
	private BooleanExpression userIdEq(String userId) {
		return StringUtils.hasText(userId) ? qYsyUserMst.username.eq(userId) : null; 
	}
	private BooleanExpression userNmEq(String userNm) {
		return StringUtils.hasText(userNm) ? qYsyUserMst.name.eq(userNm) : null; 
	}
	private BooleanExpression isEmailAuthEq(String isEmailAuth) {
		return StringUtils.hasText(isEmailAuth) ? qYsyUserMst.isEmailAuth.eq(isEmailAuth) : null; 
	}
	private BooleanExpression oauthPathEq(String oauthPath) {
		return StringUtils.hasText(oauthPath) ? qYsyUserMst.oAuthPath.eq(oauthPath) : null; 
	}
	private BooleanExpression useYnEq(String useYn) {
		return StringUtils.hasText(useYn) ? qYsyUserMst.useYn.eq(useYn) : null; 
	}
	
	
	private BooleanExpression bizCdEq(String bizCd) {
		return StringUtils.hasText(bizCd) ? qYsyBizMst.bizCd.eq(bizCd) : null; 
	}
	private BooleanExpression bizNmEq(String bizNm) {
		return StringUtils.hasText(bizNm) ? qYsyBizMst.bizNm.eq(bizNm) : null; 
	}
	
	private BooleanExpression grpIdEq(enumGrps grpId) {
		return StringUtils.hasText(grpId.toString()) ? qYsyGrpMst.grpPK.grpId.eq(grpId) : null; 
	}
	private BooleanExpression grpNmEq(String grpNm) {
		return StringUtils.hasText(grpNm) ? qYsyGrpMst.grpNm.eq(grpNm) : null; 
	}
	
	
//	private BooleanExpression addrCityEq(String addrCity) {
//		return StringUtils.hasText(addrCity) ? qYsyUserAddress.addrCity.eq(addrCity) : null; 
//	}
//	private BooleanExpression phone1Eq(String phone1) {
//		return StringUtils.hasText(phone1) ? qYsyUserAddress.phone1.eq(phone1) : null; 
//	}
//	private BooleanExpression phone2Eq(String phone2) {
//		return StringUtils.hasText(phone2) ? qYsyUserAddress.phone2.eq(phone2) : null; 
//	}
	
	
	
	/** ?????? 1??? ?????? : userId??? ?????? ????????? (UserAddress) ?????? */
//	@Transactional
//	public ResponseAuthDto<UserDto> getUserDetail(String userId) {
////		ResponseAuthDto<UserMngDto>
//		/**
//		 * ??????1. ?????? ?????? 1?????? ???????????? ?????? ????????? : ?????? ??????(????????????)
//		 *		  ex) user.get(0).ysyAddr.get(0) ??? user.get(1).ysyAddr.get(0) ??? ?????? ?????? : ??????
//		 *		  dto??? ???????????? ????????? ?????? ?????? ?????? & @JsonBackReference??? ?????? ?????? & get(0)??? ???????????? ?????? ??????
//		 * ??????2. addrList??? dto List??? ????????? jackson ?????? -> addrList ????????? ?????????
//		 * */
//		JPAQueryFactory query = new JPAQueryFactory(em);
//		
//		List<YsyUserMst> userInfoList = query
//				.select(qYsyUserMst)
//				.from(qYsyUserMst)
//				.leftJoin(qYsyBizMst)
//				.on(qYsyUserMst.ysyGrpMst.ysyBizMst.bizNm.eq(qYsyBizMst.bizNm))
//				.leftJoin(qYsyUserAddress)
//				.on(qYsyUserMst.username.eq(qYsyUserAddress.ysyUserMst.username))
//				.fetchJoin()
//				.where(qYsyUserMst.username.eq(userId))
//				.fetch();
//		
//		/** dto ?????? ?????? 1 - 0????????? 1????????? ???????????? ??????????????? 0????????? ?????? */
//		UserDto userToDto = new UserDto(userInfoList.get(0), userInfoList.get(0).getAddressList());
//		
//		return new ResponseAuthDto<UserDto>(userToDto, HttpStatus.OK); 
//	}
	
	
	@Transactional
	public ResponseAuthDto<UserDto> getFilterUserList(String userId) {
		
		
		/** ?????? ?????? start */
		YsyUserMst myInfo = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.where(qYsyUserMst.username.eq(userId))
				.fetchOne();
		
		// ????????? ?????? ???????????? bizCd??? levelId??? ?????? ?????????
		String bizCd = myInfo.getYsyGrpMst().getGrpPK().getBizCd();
		int  levelId = myInfo.getYsyGrpMst().getLevelId();
		/** ?????? ?????? end */
		
		
		/** ????????? ?????? ????????? ????????? ?????? start */
		List<YsyUserMst> userList = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.where(qYsyUserMst.username.ne(userId)
					  ,qYsyUserMst.ysyGrpMst.grpPK.bizCd.eq(bizCd)
					  ,qYsyUserMst.ysyGrpMst.levelId.goe(levelId))
				.fetch();
		/** ????????? ?????? ????????? ????????? ?????? end */
		
		List<UserDto> resultList = new ArrayList<UserDto>();
		for(YsyUserMst user : userList) {
			resultList.add(new UserDto(user));
		}
		
		return new ResponseAuthDto<UserDto>(resultList, HttpStatus.OK);
	}
	
	@Transactional
	public String modUserInfo(UserDto userDto) {
		try {
			if(!util.isNullAndEmpty(userDto.getUserId())
			&& !util.isNullAndEmpty(userDto.getUserNm())
			  ) 
			{
				
//				query
//				.select(qYsyUserMst)
//				.from(qYsyUserMst)
//				.fetchOne();
//				long execute = query
//				        .update(qYsyUserMst)
//				        .set(qYsyUserMst.name, modUserDto.getName())
//				        .where(qYsyUserMst.username.eq(modUserDto.getUsername()))
//				        .execute();
//				if(execute > 0)
//					return "ok";
//				else return "fail";
				return "fail";
			}
			else 
			{
				return  "[YsyUserMstService/modUserInfo] ModUserDto ?????? error !!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "[YsyUserMstService/modUserInfo] catch error !!";
		}
	}
	
	

}
