package com.ysy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.jwt.auth.entity.YsyGrpMenuMap;
import com.ysy.jwt.auth.entity.YsyGrpMenuMap.GrpMenuPK;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyMenuMst;
import com.ysy.jwt.auth.repository.YsyGrpMenuMapRepository;
import com.ysy.jwt.auth.repository.YsyGrpMstRepository;
import com.ysy.jwt.auth.repository.YsyMenuMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupMenuMapDummy {

	@Autowired
	YsyGrpMenuMapRepository ysyGrpMenuMapRepository;

	@Autowired
	private YsyMenuMstRepository ysyMenuMstRepository;
	@Autowired
	private YsyGrpMstRepository ysyGrpMstRepository;
	
	@Test
	public void addGroupMenuMapData() {
		System.out.println("group_menu_map dummy sample data start =========================");
		
		/*
		 * 	DEFAULT_USER, ROLE_TEMP_USER , ROLE_USER , ROLE_USER_VIP1 , ROLE_USER_VIP2 , ROLE_USER_VIP3 ,  
		 *	ROLE_MANAGER_SUPER , ROLE_MANAGER ,    
		 *	ROLE_ADMIN
		 *
		 * 메뉴 아이디 조회
		 * 그룹 조회
		 */
		
		
		List<YsyMenuMst> menuList = ysyMenuMstRepository.findAll();
		List<YsyGrpMst> grpList   = ysyGrpMstRepository.findAll();
		
		
		for (YsyMenuMst menu : menuList) {
			
			
			for (YsyGrpMst grp : grpList) {
				if(!grp.getYsyBizMst().getBizCd().equals("0001")) continue; 
				
				if(menu.getIsAdmin().equals("Y") && grp.getGrpPK().getGrpId().toString().indexOf("ADMIN") > -1) {
					
					GrpMenuPK pk = new GrpMenuPK(); 
					pk.setMenuId(menu.getMenuId());
					pk.setGrgPK(grp.getGrpPK());
					YsyGrpMenuMap ysyGrpMenuMap = YsyGrpMenuMap.builder()
							   .ysyMenuMst(menu) 
							   .ysyGrpMst(grp)
							   .mapPK(pk)
							   .build();
					
					ysyGrpMenuMapRepository.save(ysyGrpMenuMap);
				}else if(menu.getIsAdmin().equals("Y") && grp.getGrpPK().getGrpId().toString().indexOf("MANAGER") > -1){
					GrpMenuPK pk = new GrpMenuPK(); 
					pk.setMenuId(menu.getMenuId());
					pk.setGrgPK(grp.getGrpPK());
					YsyGrpMenuMap ysyGrpMenuMap = YsyGrpMenuMap.builder()
							   .ysyMenuMst(menu) 
							   .ysyGrpMst(grp)
							   .mapPK(pk)
							   .build();
					ysyGrpMenuMapRepository.save(ysyGrpMenuMap);
				}
				
			}
			
			
			if(menu.getPMenuId()== null || menu.getPMenuId().equals("")) {//상위 부모 메뉴들
				
			}else {//하위 자식 메뉴들
				
			}
		}
		
		
		
		
		
		
		System.out.println("group_menu_map dummy sample data end =========================");
	}
}
