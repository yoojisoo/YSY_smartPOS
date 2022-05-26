package com.ysy.jwt.auth.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.dto.MenuDto;
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
	
	public List<Object[]> getMenuList() {
	
		Sort sort = util.getSort("pMenuId","menuSeq");
		System.out.println("sorttttttttttttt");
		
		List<Object[]> resultList = ysyMenuMstRepository.getDefaultMenuList();
//		private String menu_id;
//		private String p_menu_id;
//		private String menu_nm;
//		private String menu_path;
//		private String menu_full_path;
//		private Integer menu_seq;
//		private String is_admin;
		List<MenuDto> menuList = 
				resultList
					.stream()
					.map(x -> MenuDto.builder()
							 	.menu_id       ((String)x[0])
							 	.p_menu_id     ((String)x[1])
							 	.menu_nm       ((String)x[2])
							 	.menu_path     ((String)x[3])
							 	.menu_full_path((String)x[4])
							 	.menu_seq      ((Integer)x[5])
							 	.is_admin      ((String)x[6])
							 	.build()
						)
					.collect(Collectors.toList());
		
		return resultList;
//		return ysyMenuMstRepository.findAll();
		
	}
}
