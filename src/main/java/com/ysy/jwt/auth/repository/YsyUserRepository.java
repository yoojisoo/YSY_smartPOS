package com.ysy.jwt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyUser;

public interface YsyUserRepository extends JpaRepository<YsyUser, Long>{

	public YsyUser findByUsername(String username);
}
