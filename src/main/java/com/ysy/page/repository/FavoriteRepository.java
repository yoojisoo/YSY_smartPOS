package com.ysy.page.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysy.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	
//	@Query(""
//			+ "select a,b form FAVORITE_MST a inner join FAVORITE_DTL b on a.menu_name = b.menu_name"
//			+ "")
//	public void getSampleDataList(); 
}
