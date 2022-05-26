package com.ysy.jwt.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.jwt.auth.dto.MenuDtoIf;
import com.ysy.jwt.auth.entity.YsyMenuMst;
import com.ysy.jwt.auth.service.YsyMenuMstService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("ysy/v1/menu")
//@AllArgsConstructor
public class YsyMenuController {

	@Autowired
	private  YsyMenuMstService ysyMenuMstService;
	
	@GetMapping("/getMenuList")
	public List<MenuDtoIf> getMenuList() {
		System.out.println("123423123123");
		return ysyMenuMstService.getMenuList();
	}
}
