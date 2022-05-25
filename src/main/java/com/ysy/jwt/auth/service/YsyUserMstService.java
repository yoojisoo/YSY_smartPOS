package com.ysy.jwt.auth.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysy.common.SysEnum;
import com.ysy.common.SysEnum.enumGrps;
import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.JoinDto;
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
	
	/** user 등록 */
	@Transactional
	public String signUp(JoinDto joinDto) {
		
		try 
		{
			if (!util.isNullAndEmpty(joinDto.getBizCd())
			 && !util.isNullAndEmpty(joinDto.getUsername())
			 && !util.isNullAndEmpty(joinDto.getPassword())
			 && !util.isNullAndEmpty(joinDto.getName())) {

				/** 0. 유저 존재 확인 (존재하면 메세지 return / 존재하지 않으면 다음 단계) */
				if (isUser(joinDto.getUsername())) return "error : user 존재";
				
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
						return "회사코드가 있습니다.";
					}
				}
//				else {
					/** 존재함 -> bizCd가 존재하면 Default grpId도 있기 때문에 Group 정보를 가져옴 */
					ysyBizMst = ysyBizRepository.findById(bizCd).get();
					ysyGrpMst = ysyGrpService.getGrpData(bizCd, grpId).get();
//				}
				
				/** 2. 최종 저장 양식은 YsyUserMst라서 정보를 옮겨 담아줌 */
				YsyUserMst ysyUser = YsyUserMst.builder()
						.username(joinDto.getUsername())
						.password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
						.name(joinDto.getName())
						.ysyGrpMst(ysyGrpMst)
						.build();

				ysyUserRepository.save(ysyUser);
				
				return "ok";
			} else {
				return "error : Id or password or name is empty!";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			return "error : user register error!";
		}
		
	}
	
	/** user 존재여부 확인 존재 : true */
	public boolean isUser(String username) {
	
		if(ysyUserRepository.findByUsername(username) == null)
			return false;
		
		return true;
	}
}
