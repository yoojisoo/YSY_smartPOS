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

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.common.SysEnum;
import com.ysy.common.SysEnum.enumGrps;
import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.JoinDto;
import com.ysy.jwt.auth.dto.MenuDto;
import com.ysy.jwt.auth.dto.ModUserDto;
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.dto.UserMngDto;
import com.ysy.jwt.auth.entity.QYsyBizMst;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
import com.ysy.jwt.auth.entity.QYsyUserAddress;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.repository.YsyBizMstRepository;
import com.ysy.jwt.auth.repository.YsyGrpMstRepository;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;

@Service
public class YsyUserMstService {
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
	private YsyUtil ysyUtil;
	
	/** 2022 07 07 mnew2m
	 * 사용하는 Q Class List */
	QYsyUserMst     qYsyUserMst 	= QYsyUserMst.ysyUserMst;
	QYsyGrpMst     qYsyGrpMst 	= QYsyGrpMst.ysyGrpMst;
	QYsyGrpMenuMap 	qYsyGrpMenuMap  = QYsyGrpMenuMap.ysyGrpMenuMap;
	QYsyUserAddress qYsyUserAddress = QYsyUserAddress.ysyUserAddress;	
	QYsyBizMst		qYsyBizMst 		= QYsyBizMst.ysyBizMst;	
	
	/** user 등록 */
	@Transactional
	public ResponseAuthDto<String> signUp(JoinDto joinDto) {
		
		ResponseAuthDto<String> resDto = new ResponseAuthDto<String>();
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		try 
		{
			if (!util.isNullAndEmpty(joinDto.getBizCd())
			 && !util.isNullAndEmpty(joinDto.getUsername())
			 && !util.isNullAndEmpty(joinDto.getPassword())
			 && !util.isNullAndEmpty(joinDto.getName())) 
			{
				
				/** 0. 유저 존재 확인 (존재하면 메세지 return / 존재하지 않으면 다음 단계) */
				if (isUser(joinDto.getUsername())) {
					YsyUserMst userInfo = userInfo(joinDto.getUsername());
					String orgPath     = userInfo.getOAuthPath() == null || userInfo.getOAuthPath().equals("") ? "사이트" : userInfo.getOAuthPath();
					String joinDtoPath = joinDto.getOAuthPath()  == null || joinDto.getOAuthPath().equals("")  ? "사이트" : joinDto.getOAuthPath();
					
					if(orgPath.equals(joinDtoPath)) 
					{
						resDto.setMsg("ok");
						resDto.setStatus(HttpStatus.OK);
						return resDto;
					}
					
					String errorMsg =  "error : \n"
							         + "["+joinDto.getUsername() +"] 해당 메일은 " + orgPath + "로 가입되었으며 , "
							         + "로그인은 " + joinDto.getOAuthPath() + "로 진행중입니다.\n"
					                 + orgPath +"로 로그인 해주세요!!";
					resDto.setMsg(errorMsg);
					resDto.setStatus(HttpStatus.BAD_REQUEST);
					return resDto;
				}
				
				// bizCd와 Default Group ID 셋팅
				String bizCd = joinDto.getBizCd();
				enumGrps grpId = SysEnum.enumGrps.ROLE_TEMP_USER;
				List<enumGrps> enumTemp = Stream.of(SysEnum.enumGrps.values()).collect(Collectors.toList());
				int enumRoleNum;
				
				YsyBizMst ysyBizMst = new YsyBizMst();
				YsyGrpMst ysyGrpMst = new YsyGrpMst();
				
				/** 1. bizCd 존재 확인 */
				if (!ysyBizService.isBizCd(bizCd)) {
					enumRoleNum = 1;
					
					/** 존재하지 않음 -> bizCd가 존재하지 않으면 Default grpId도 없기 때문에 둘 다 새로 등록해줌 */
					ysyBizMst = YsyBizMst.builder().bizCd(bizCd).bizNm("").useYn("Y").delYn("N").build(); // biz 등록
					ysyBizRepository.save(ysyBizMst);
					
					for(enumGrps enumRole : enumTemp) {
						GrpPK grpPK = new GrpPK(enumRole, bizCd); // grp 등록에 쓸 Pk 정보를 넣어줌
						ysyGrpMst = YsyGrpMst.builder()
								.grpPK(grpPK)
								.ysyBizMst(ysyBizMst)
								.grpNm("")
								.useYn("Y")
								.levelId(enumRoleNum)
								.build(); // grp 등록
						
						ysyGrpRepository.save(ysyGrpMst);
						enumRoleNum++;
					}
					
					if (!ysyBizService.createBiz(ysyBizMst)) {
						resDto.setMsg("회사코드가 있습니다.");
						resDto.setStatus(HttpStatus.BAD_REQUEST);
						return resDto;//"회사코드가 있습니다.";
					}
				}
				
				/** 존재함 -> bizCd가 존재하면 Default grpId도 있기 때문에 Group 정보를 가져옴 */
				ysyGrpMst = query
						.select(qYsyGrpMst)
						.from(qYsyGrpMst)
						.innerJoin(qYsyGrpMst.ysyBizMst, qYsyBizMst).fetchJoin()
						.where(qYsyGrpMst.ysyBizMst.bizCd.eq(bizCd)
							 , qYsyGrpMst.grpPK.grpId.eq(grpId))
						.fetchOne();
				
				/** 2. 최종 저장 양식은 YsyUserMst라서 정보를 옮겨 담아줌 */
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
//						.orElseThrow(()->  new IllegalArgumentException("id가 존재하지 않습니다.") );
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
//				return  "[YsyUserMstService/modUserInfo] ModUserDto 정보 error !!";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "[YsyUserMstService/modUserInfo] catch error !!";
//		}
//	}
	
	/** ID에 해당하는 userInfo return */
	public YsyUserMst userInfo(String username) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		YsyUserMst user = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.where(qYsyUserMst.username.eq(username))
				.fetchOne();
		
		return user;
	}
	
	/** user 존재여부 확인 존재 : true */
	public boolean isUser(String username) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
		YsyUserMst user = query
				.select(qYsyUserMst)
				.from(qYsyUserMst)
				.where(qYsyUserMst.username.eq(username))
				.fetchOne();
		
		if(user == null)
			return false;
		return true;
	}
	
	/** isAdmin 체크 : admin이면 true */
	public boolean isAdmin(String username) {
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		
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
