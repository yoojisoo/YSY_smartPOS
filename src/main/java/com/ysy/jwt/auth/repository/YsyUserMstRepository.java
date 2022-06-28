package com.ysy.jwt.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ysy.jwt.auth.entity.YsyUserMst;

public interface YsyUserMstRepository extends JpaRepository<YsyUserMst, String>{

	public YsyUserMst findByUsername(String username);
	
	public int deleteByUsername(String username);
	
	@Query(value="SELECT  a.user_id\r\n"
			+ "		, a.user_phone\r\n"
			+ "		, a.user_nm\r\n"
			+ "		, DATE_FORMAT(a.reg_dt,'%Y-%m-%d')\r\n"
			+ "		, a.grp_id\r\n"
			+ "		, a.biz_cd\r\n"
			+ "		, b.grp_nm\r\n"
			+ "FROM ysy_user_mst a\r\n"
			+ "LEFT OUTER\r\n"
			+ "JOIN ysy_grp_mst b\r\n"
			+ "ON   a.biz_cd = b.biz_cd\r\n"
			+ "AND  a.grp_id = b.grp_id\r\n"
			+ ";", nativeQuery = true)
	public List<Object[]> getDefaultUserList();
}
