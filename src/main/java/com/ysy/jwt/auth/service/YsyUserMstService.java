package com.ysy.jwt.auth.service;

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
import com.ysy.jwt.auth.dto.ModUserDto;
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.entity.QYsyBizMst;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
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
	
	/** 2022 07 15 mnew2m
	 * 사용하는 Q Class */
	QYsyUserMst qYsyUserMst = QYsyUserMst.ysyUserMst;
	QYsyBizMst  qYsyBizMst  = QYsyBizMst.ysyBizMst;
	QYsyGrpMst  qYsyGrpMst  = QYsyGrpMst.ysyGrpMst;
	
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
	
	public String modUserInfo(ModUserDto modUserDto) {
		try {
			if(!util.isNullAndEmpty(modUserDto.getUsername())
			&& !util.isNullAndEmpty(modUserDto.getName())
			&& !util.isNullAndEmpty(modUserDto.getPassword())) 
			{
				YsyUserMst orgYsyUser = ysyUserRepository.findById(modUserDto.getUsername())
						.orElseThrow(()->  new IllegalArgumentException("id가 존재하지 않습니다.") );
				YsyUserMst ysyUser = YsyUserMst.builder()
						.username(modUserDto.getUsername())
						.password(bCryptPasswordEncoder.encode(modUserDto.getPassword()))
						.name(modUserDto.getName())
						.ysyGrpMst(orgYsyUser.getYsyGrpMst())
						.oAuthPath(orgYsyUser.getOAuthPath())
						.build();
				ysyUserRepository.save(ysyUser);
				
				return "ok";
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
	

}
