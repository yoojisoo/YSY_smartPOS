package com.ysy.jwt.auth.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.jwt.auth.entity.YsyMenuMst;

public interface YsyMenuMstRepository extends JpaRepository<YsyMenuMst, String>{//
	
	
	public List<YsyMenuMst> findByUseYn(String useYn ,Sort sort);

//	@Query("SELECT A FROM YSY_MENU_MST A "
//		+ " INNER "
//		+ " JOIN"
//		)
//	public YsyMenuMst getMenuList(String username);
	
	
	
//	@Query("")
//	public List<Object> getMenuList
}
