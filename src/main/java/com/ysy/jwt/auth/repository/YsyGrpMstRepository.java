package com.ysy.jwt.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;

public interface YsyGrpMstRepository extends JpaRepository<YsyGrpMst, GrpPK>{

//GreaterThanEqual 크거나 같다.
	public List<YsyGrpMst> findByLevelIdGreaterThanEqual(int levelId);
	
//	public YsyGrpMst fingByGrpId(String grpId);
//	public YsyGrpMst findByBizCdAndGrpId(String bizCd, String grpId);
}
