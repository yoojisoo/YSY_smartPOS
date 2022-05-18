package com.ysy.jwt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;

public interface YsyGrpMstRepository extends JpaRepository<YsyGrpMst, GrpPK>{

//	public YsyGrpMst fingByGrpId(String grpId);
//	public YsyGrpMst findByBizCdAndGrpId(String bizCd, String grpId);
}