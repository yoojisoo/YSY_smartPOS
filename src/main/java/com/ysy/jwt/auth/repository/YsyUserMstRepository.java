package com.ysy.jwt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyUserMst;

public interface YsyUserMstRepository extends JpaRepository<YsyUserMst, Long>{

	public YsyUserMst findByUsername(String username);
}