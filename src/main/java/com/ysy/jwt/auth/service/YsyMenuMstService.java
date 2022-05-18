package com.ysy.jwt.auth.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.entity.YsyMenuMst;
import com.ysy.jwt.auth.repository.YsyMenuMstRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class YsyMenuMstService {

	private final YsyMenuMstRepository ysyMenuMstRepository;
	private final YsyUtil util;
	
	public List<YsyMenuMst> getMenuList() {
	
		Sort sort = util.getSort("pMenuId","menuSeq");
		System.out.println("sorttttttttttttt");
		return ysyMenuMstRepository.findByUseYn("Y" , sort);
//		return ysyMenuMstRepository.findAll();
		
	}
}
