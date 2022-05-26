package com.ysy.jwt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;

public interface YsyGrpMenuMapRepository extends JpaRepository<YsyGrpMenuMap, GrpPK>{

}
