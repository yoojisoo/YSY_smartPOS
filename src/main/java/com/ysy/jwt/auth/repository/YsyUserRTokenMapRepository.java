package com.ysy.jwt.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyUserRTokenMap;

public interface YsyUserRTokenMapRepository extends JpaRepository<YsyUserRTokenMap, String>{

}
