package com.ysy.jwt.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.MenuDtoIf;
import com.ysy.jwt.auth.entity.YsyMenuMst;
import com.ysy.jwt.auth.repository.YsyGrpMenuMapRepository;
import com.ysy.jwt.auth.repository.YsyMenuMstRepository;

@Service
//@AllArgsConstructor
public class YsyMenuMstService {
	
	@Autowired
	private  YsyMenuMstRepository ysyMenuMstRepository;
	@Autowired
	private  YsyGrpMenuMapRepository ysyGrpMenuMapRepository;
	@Autowired
	private YsyUtil util;
	
	public List<MenuDtoIf> getMenuList() {
	
		Sort sort = util.getSort("pMenuId","menuSeq");
		System.out.println("sorttttttttttttt");
		
		List<MenuDtoIf> menuList = ysyMenuMstRepository.getDefaultMenuList();
		
//		List<Object[]> list = ysyGrpMenuMapRepository.getDefaultMenu();
		
		return menuList;
//		return ysyMenuMstRepository.findAll();
		
	}
}
