package com.ysy.jwt.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysy.common.SysEnum;
import com.ysy.common.SysEnum.enumGrps;
import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.model.JoinModel;
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
	YsyGrpMstRepository ysyGrpMstRepository;
	
	/** user 등록 */
	@Transactional
	public String signUp(JoinModel joinModel) {
		
//		GrpPK pk = new GrpPK();
//		GrpPK pk = joinModel.getYsyGrpMst().getGrpPK();
//		ysyGrpMstRepository.findById(pk);
		
		try 
		{
			if (!YsyUtil.isNullAndEmpty(joinModel.getUsername())
			 && !YsyUtil.isNullAndEmpty(joinModel.getPassword())
			 && !YsyUtil.isNullAndEmpty(joinModel.getName())) {

				/** 0. 유저 존재 확인 (존재하면 메세지 return / 존재하지 않으면 다음 단계) */
				if (isUser(joinModel.getUsername())) return "error : user 존재";
				
				// bizCd와 Default Group ID 셋팅
				String bizCd = joinModel.getBizCd();
				enumGrps grpId = SysEnum.enumGrps.ROLE_TEMP_USER;
				
//				YsyBizMst ysyBizMst = ysyBizService.getBizData(bizCd);
				YsyBizMst ysyBizMst = new YsyBizMst();
				YsyGrpMst ysyGrpMst = new YsyGrpMst();
				
				/** 1. bizCd 존재 확인 */
				if (!ysyBizService.isBizCd(bizCd)) {
					/** 존재하지 않음 -> bizCd가 존재하지 않으면 Default grpId도 없기 때문에 둘 다 새로 등록해줌 */
					ysyBizMst = YsyBizMst.builder().bizCd(bizCd).bizNm("").useYn("Y").delYn("N").build(); // biz 등록
					ysyGrpMst.getGrpPK().setBizCd(bizCd); // grp 등록에 쓸 Pk 정보를 넣어줌
					ysyGrpMst.getGrpPK().setGrpId(grpId); // grp 등록에 쓸 Pk 정보를 넣어줌
					ysyGrpMst = YsyGrpMst.builder().grpPK(ysyGrpMst.getGrpPK()).grpNm("").useYn("Y").build(); // grp 등록
					
					if (!ysyBizService.createBiz(ysyBizMst)) {
						return "회사코드가 있습니다.";
					}
				} else {
					/** 존재함 -> bizCd가 존재하면 Default grpId도 있기 때문에 Group 정보를 가져옴 */
					ysyGrpMst = ysyGrpService.getGrpData(bizCd, grpId).get();
				}
				
				/** 2. 최종 저장 양식은 YsyUserMst라서 정보를 옮겨 담아줌 */
				YsyUserMst ysyUser = new YsyUserMst();
				ysyUser.setUsername(joinModel.getUsername());
				ysyUser.setPassword(bCryptPasswordEncoder.encode(joinModel.getPassword()));
				ysyUser.setName(joinModel.getName());
				ysyUser.setYsyGrpMst(ysyGrpMst);

				ysyUserRepository.save(ysyUser);
				
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
