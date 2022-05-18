package com.ysy.jwt.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysy.common.SysEnum.enumGrps;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;
import com.ysy.jwt.auth.repository.YsyGrpMstRepository;

@Service
public class YsyGrpMstService {
	
	@Autowired
	YsyGrpMstRepository ysyGrpRepository;
	
	/** grp 존재여부 확인. 존재하면 true return */
	public boolean isGrpId(String bizCd, enumGrps grpId) {
		GrpPK grpPK = new GrpPK(grpId, bizCd);
		
		if(ysyGrpRepository.findById(grpPK) == null)
			return false;
		return true;
	}
	
	public Optional<YsyGrpMst> getGrpData(String bizCd, enumGrps grpId) {
		GrpPK grpPK = new GrpPK(grpId, bizCd);
		
		return ysyGrpRepository.findById(grpPK);
	}
}
