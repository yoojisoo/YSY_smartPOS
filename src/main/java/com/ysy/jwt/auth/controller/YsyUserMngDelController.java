package com.ysy.jwt.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.jwt.auth.service.YsyUserMngDelService;

@RestController
@CrossOrigin
@RequestMapping("/ysy/v1/admin")
public class YsyUserMngDelController {

	@Autowired
	YsyUserMngDelService ysyUserMngDelService;
	
	/** 2022 06 16 yoojisoo delete userInfo from the grid */
	@PostMapping("/delGridUserInfo")
	public void delGridUserInfo(@RequestBody List<String> usernameList) {
		System.out.println("/delGridUserInfo 들어옴 ---------------------------> ");
		ysyUserMngDelService.delGridUserInfo(usernameList);
	}
	
	
	
}
