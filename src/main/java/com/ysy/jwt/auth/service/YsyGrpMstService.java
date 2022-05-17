package com.ysy.jwt.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.repository.YsyGrpMstRepository;

@Service
public class YsyGrpMstService {
	
	@Autowired
	YsyGrpMstRepository ysyGrpRepository;
//	@Autowired
	YsyGrpMst ysyGrpMst;
	
	/** grp 존재여부 확인. 존재하면 true return */
	public boolean isGrpId(String bizCd, String grpId) {
		ysyGrpMst.getPk().setBizCd(bizCd);
		ysyGrpMst.getPk().setGrpId(grpId);
		
		if(ysyGrpRepository.findById(ysyGrpMst.getPk()) == null)
			return false;
		return true;
	}
	
	public Optional<YsyGrpMst> getGrpData(String bizCd, String grpId) {
		ysyGrpMst.getPk().setBizCd(bizCd);
		ysyGrpMst.getPk().setGrpId(grpId);
		
		return ysyGrpRepository.findById(ysyGrpMst.getPk());
	}
}
