package com.ysy.jwt.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyGrpMenuMap.GrpMenuPK;

public interface YsyGrpMenuMapRepository extends JpaRepository<YsyGrpMenuMap, GrpMenuPK>{

	/*
	 * select distinct b from YsyGrpMenuMap a join a.ysyMenuMst b
	 * where 1=1
	 * and   a.grp_id = 'DEFAULT_USER'
	 * and   b.useYn = 'Y'
	 * and   b.isAdmin = 'N'
	 * 
	 * */
	
//	@Query("select distinct b from YsyGrpMenuMap a join a.ysyMenuMst b "
//			+ " where 1=1"
//			+ "   and a.grp_id = 'DEFAULT_USER'                          "
//			+ "   and b.useYn = 'Y'                                      "
//			+ "   and b.isAdmin = 'N'                                    ")
//	public List<Object[]> getDefaultMenu();
}
