package com.ysy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.common.SysEnum;
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
	private YsyMenuMstRepository ysyMenuMstRepository;
	@Autowired
	private YsyGrpMstRepository  ysyGrpMstRepository;
	@Autowired
	private YsyGrpMenuMapRepository ysyGrpMenuMapRepository;
	
	@Test
	public void addGroupMenuMapData() {
		System.out.println("group_menu_map dummy sample data start =========================");
		
		/*
		 * 	DEFAULT_USER, ROLE_TEMP_USER, ROLE_USER, ROLE_USER_VIP1, ROLE_USER_VIP2, ROLE_USER_VIP3,  
		 *	ROLE_MANAGER_SUPER, ROLE_MANAGER, ROLE_ADMIN
		 *
		 *  1.메뉴 아이디 조회
		 *  2.그룹 조회
		 *  3.메뉴 마다 그룹 role을 넣는 것
		 */
			
		List<YsyMenuMst> menuList = ysyMenuMstRepository.findAll();
		List<YsyGrpMst>  grpList  = ysyGrpMstRepository.findAll();
		
		List<YsyMenuMst> pMenuList = menuList.stream()
				                             .filter(x->x.getPMenuId().isEmpty()).collect(Collectors.toList());
		List<YsyMenuMst> childMenuList =menuList.stream().filter(x->!x.getPMenuId().isEmpty()).collect(Collectors.toList());
		
		for(YsyMenuMst pmenu : pMenuList)
		{
			String pkey = "";
			if(pmenu.getMenuNm().equals("공지사항")) {
				System.out.println("공지사항 save");
				pkey = pmenu.getMenuId();
				YsyGrpMst yy = grpList.stream()
						              .filter(x->x.getYsyBizMst().getBizCd().equals("0001") 
						            		  && x.getGrpPK().getGrpId().toString().equals("DEFAULT_USER"))
						              .findAny().orElse(null);
				setEntity(pmenu, yy);
				List<YsyMenuMst> resultList = new ArrayList<YsyMenuMst>();
				for (YsyMenuMst childMenu : childMenuList) 
				{
					if(childMenu.getPMenuId().equals(pkey)) 
					{
						System.out.println("공지사항 child save");
						resultList.add(childMenu);
						
						setEntity(childMenu, yy);
						
					}
				}
			}
			else if(pmenu.getMenuNm().equals("매장현황") || pmenu.getMenuNm().equals("배달현황")) {
				pkey = pmenu.getMenuId();
				YsyGrpMst yy = grpList.stream().filter(x->x.getYsyBizMst().getBizCd().equals("0001") 
						                               && x.getGrpPK().getGrpId().toString().equals("ROLE_USER"))
						                       .findAny().orElse(null);
				setEntity(pmenu, yy);
				List<YsyMenuMst> resultList = new ArrayList<YsyMenuMst>();
				for (YsyMenuMst childMenu : childMenuList) 
				{
					if(childMenu.getPMenuId().equals(pkey)) 
					{
						resultList.add(childMenu);
						
						setEntity(childMenu, yy);
						
					}
				}
			}
			else {
				pkey = pmenu.getMenuId();
				YsyGrpMst yy = grpList.stream().filter(x->x.getYsyBizMst().getBizCd().equals("0001") 
						&& x.getGrpPK().getGrpId().toString().equals("ROLE_MANAGER"))
						.findAny().orElse(null);
				setEntity(pmenu, yy);
				List<YsyMenuMst> resultList = new ArrayList<YsyMenuMst>();
				for (YsyMenuMst childMenu : childMenuList) 
				{
					if(childMenu.getPMenuId().equals(pkey)) 
					{
						resultList.add(childMenu);
						
						setEntity(childMenu, yy);
						
					}
				}
			}
			
//			String pMenuId = pmenu.getMenuId();
//			
//			for(YsyMenuMst cmenu : childMenuList)
//			{
//				for(YsyGrpMst grp : grpList)
//				{
//					
//					if(!grp.getGrpPK().getBizCd().equals("0001")) continue;
//					
//					/* 관리자 페이지 */
//					if(pmenu.getIsAdmin().equals("Y") && cmenu.getIsAdmin().equals("Y"))
//					{
//						if(grp.getGrpPK().getGrpId().toString().indexOf("MANAGER") > -1) {
//							setEntity(pmenu, grp);
//							setEntity(cmenu, grp);
//						}
//					}
//					
//					/* 유저 페이지 */
//					if(pmenu.getIsAdmin().equals("N") && cmenu.getIsAdmin().equals("N"))
//					{
//						if(pMenuId.equals(pmenu.getMenuNm()) && pMenuId.equals(cmenu.getPMenuId()) && grp.getGrpPK().getGrpId().equals(SysEnum.enumGrps.DEFAULT_USER))
//						{
//							setEntity(pmenu, grp);
//							setEntity(cmenu, grp);
//						}
//				
//						
//					}
//				}
//			}
		}
		
		System.out.println("group_menu_map dummy sample data end =========================");
	}

	private void setEntity(YsyMenuMst menu, YsyGrpMst grp) {
		GrpMenuPK grpMenuPK = new GrpMenuPK();
		grpMenuPK.setMenuId(menu.getMenuId());
		grpMenuPK.setGrgPK(grp.getGrpPK()); //grpId, bizCd
		
		YsyGrpMenuMap ysyGrpMenuMap = YsyGrpMenuMap.builder()
				.mapPK(grpMenuPK)
				.menuNm(menu.getMenuNm())
				.ysyGrpMst(grp)
				.ysyMenuMst(menu)
				.build();
		System.out.println("setEntity    ");
		ysyGrpMenuMapRepository.save(ysyGrpMenuMap);
		
	}
	
}
