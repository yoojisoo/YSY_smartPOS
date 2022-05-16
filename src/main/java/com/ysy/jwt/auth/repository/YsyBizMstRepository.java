package com.ysy.jwt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyBizMst;

public interface YsyBizMstRepository extends JpaRepository<YsyBizMst, String>{

	public YsyBizMst findByBizCd(String bizCd);
}
