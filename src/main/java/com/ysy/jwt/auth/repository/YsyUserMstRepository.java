package com.ysy.jwt.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ysy.jwt.auth.entity.YsyUserMst;

public interface YsyUserMstRepository extends JpaRepository<YsyUserMst, String>{

	public YsyUserMst findByUsername(String username);
	
	public int deleteByUsername(String username);
	
	
	
	@Query(value=" SELECT  a.user_id                         "
			   + " 		, a.user_phone                       "
			   + " 		, a.user_nm                          "
			   + " 		, DATE_FORMAT(a.reg_dt,'%Y-%m-%d')   "
			   + " 		, a.grp_id                           "
			   + " 		, a.biz_cd                           "
			   + " 		, b.grp_nm                           "
			   + " FROM ysy_user_mst a                       "
			   + " LEFT OUTER                                "
			   + " JOIN ysy_grp_mst b                        "
			   + " ON   a.biz_cd = b.biz_cd                  "
			   + " AND  a.grp_id = b.grp_id                  "
			   + ";"
			   , nativeQuery = true)
	public List<Object[]> getDefaultUserList();
	
	
	//test query
	@Query("select distinct u from YsyUserMst u left join u.addressList")
	List<YsyUserMst> findAllJPQL();
	
	@Query("select distinct u from YsyUserMst u left join fetch u.addressList")
	List<YsyUserMst> findAllJPQLFetch();
}
