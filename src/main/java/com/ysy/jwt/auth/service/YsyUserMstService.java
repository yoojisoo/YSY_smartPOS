package com.ysy.jwt.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;
import com.ysy.jwt.auth.entity.YsyUserMst;
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
	YsyGrpMstRepository ysyGrpMstRepository;
	
	/** user 등록 */
	@Transactional
	public String signUp(YsyUserMst ysyUserMst ) {
		
//		GrpPK pk = new GrpPK();
		GrpPK pk = ysyUserMst.getYsyGrpMst().getGrpPK();
		ysyGrpMstRepository.findById(pk);
		
		try 
		{
			if (!YsyUtil.isNullAndEmpty(ysyUserMst.getUsername()) && !YsyUtil.isNullAndEmpty(ysyUserMst.getPassword())
					&& !YsyUtil.isNullAndEmpty(ysyUserMst.getName())) {

				if (isUser(ysyUserMst.getUsername())) {
					return "error : user 존재";
				}
				
				;
				String bizCd = ysyUserMst.getYsyGrpMst().getYsyBizMst().getBizCd();
//				String bizCd = "0001";
				YsyBizMst ysyBizMst = ysyBizService.getBizData(bizCd);
				
				if (!ysyBizService.isBizCd(bizCd)) {
					ysyBizMst = YsyBizMst.builder().bizCd(bizCd).bizNm("").useYn("Y").delYn("N").build();
					if (!ysyBizService.createBiz(ysyBizMst)) {
						return "회사코드가 있습니다.";
					}
				}
				
				//grp ROLE_TEMP_USER
				
				ysyUserMst.setPassword(bCryptPasswordEncoder.encode(ysyUserMst.getPassword()));
//				ysyUserMst.setRoles(SysEnum.grps.ROLE_TEMP_USER.toString());// 기본 룰 셋팅 , 이후 관리자 페이지에서 role 변경
//				ysyUserMst.setYsyBiz(ysyBizMst);

				ysyUserRepository.save(ysyUserMst);

				return "ok -> login page move!";
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
