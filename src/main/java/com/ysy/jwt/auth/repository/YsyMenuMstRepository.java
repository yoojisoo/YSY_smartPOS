package com.ysy.jwt.auth.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ysy.jwt.auth.dto.MenuDtoIf;
import com.ysy.jwt.auth.entity.YsyMenuMst;

public interface YsyMenuMstRepository extends JpaRepository<YsyMenuMst, String>{//
	
	
	public List<YsyMenuMst> findByUseYn(String useYn ,Sort sort);
	
	/** default 메뉴 목록 */
	@Query(value=" SELECT                                         "
			+ "           a.menu_id                               " // " //as menuId        
				+ "     , a.p_menu_id                             " // " //as pMenuId       
				+ "     , a.menu_nm                               " // " //as menuNm        
				+ "     , a.menu_path                             " // " //as menuPath      
				+ "     , a.menu_full_path                        " // " //as menuFullPath  
				+ "     , a.menu_seq                              " // " //as menuSeq       
				+ "     , a.is_admin                              " // " //as isAdmin       
				+ " FROM  (                                       "
				+ "         SELECT a.*                            "
				+ "         FROM   Ysy_Menu_Mst a                 " 
				+ "         WHERE  1=1                            "
				+ "         AND    use_yn = 'Y'                   "
//				+ "         AND    is_admin = 'N'                 "
				+ " 	  ) a                                     "
				+ " INNER                                         "
				+ " JOIN  (                                       "
				+ "         SELECT distinct a.menu_id , a.grp_id  "
				+ "         FROM   Ysy_Grp_Menu_Map a             "
				+ "         WHERE  1=1                            "
//				+ "         AND    a.grp_id = 'DEFAULT_USER'      "
				+ " 	  ) b		                              "
				+ " ON    a.menu_id = b.menu_id                   "
				+ "" , nativeQuery = true)//, nativeQuery = true
	public List<Object[]> getDefaultMenuList();

/*
 * 

SELECT a.menu_id , a.p_menu_id , a.menu_nm , a.menu_path , a.menu_full_path , a.menu_seq , a.is_admin 
FROM  (
      	SELECT * FROM ysy_menu_mst
			WHERE 1=1
			AND   use_yn = 'y'
			AND   is_admin = 'n'
		) a
INNER 
JOIN (
     	SELECT distinct menu_id , grp_id
		FROM   ysy_grp_menu_map
		WHERE 1=1
		AND grp_id = 'DEFAULT_USER'
	  ) b		
ON   a.menu_id = b.menu_id

 * 
 * 
 * */
}
