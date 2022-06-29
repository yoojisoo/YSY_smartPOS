package com.ysy.jwt.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ysy.jwt.auth.entity.YsyUserAddress;
import com.ysy.jwt.auth.entity.YsyUserMst;

public interface YsyUserAddressRepository extends JpaRepository<YsyUserAddress, Long>{

//	YsyUserMst ysyUserMst
	public List<YsyUserAddress> findByYsyUserMst(YsyUserMst ysyUserMst);
	
	//test query
	@Query(" select u               "
		+ "  from YsyUserAddress u  "
		+ "  left join u.ysyUserMst "
		+ "  where u.ysyUserMst.username = :userId")
	public List<YsyUserAddress> getUserInfoJPQL(@Param("userId") String userId);

	
	
	
}
