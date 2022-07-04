package com.ysy.jwt.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.jwt.auth.dto.MenuDto;
import com.ysy.jwt.auth.service.YsyMenuMstService;

@RestController
@RequestMapping("ysy/v1/menu")
//@AllArgsConstructor
public class YsyMenuController {

	@Autowired
	private  YsyMenuMstService ysyMenuMstService;
	
	@GetMapping("/findMenuList")
	public List<MenuDto> findMenuList() {
		return ysyMenuMstService.findMenuList();
	}
}
