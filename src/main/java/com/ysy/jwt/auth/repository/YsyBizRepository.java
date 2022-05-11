package com.ysy.jwt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyBiz;

public interface YsyBizRepository extends JpaRepository<YsyBiz, Long>{

	public YsyBiz findByBizCd(String bizCd);
}
