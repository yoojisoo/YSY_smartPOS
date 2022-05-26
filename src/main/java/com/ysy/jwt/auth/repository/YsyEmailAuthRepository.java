package com.ysy.jwt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyEmailAuth;

public interface YsyEmailAuthRepository extends JpaRepository<YsyEmailAuth, Long>{

	public YsyEmailAuth findByTmpEmail(String email);
}
