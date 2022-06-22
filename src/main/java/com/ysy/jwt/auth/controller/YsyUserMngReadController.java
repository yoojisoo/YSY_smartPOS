package com.ysy.jwt.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.service.YsyUserMngReadService;

@RestController
@CrossOrigin
@RequestMapping("/ysy/v1")
public class YsyUserMngReadController {

	@Autowired
	YsyUserMngReadService ysyUserMngReadService;
	
	/** 2022 06 02 yoojisoo get data sample */
	@GetMapping("/getUserList")
	public List<YsyUserMst> getUserList(){
		System.out.println("getUserList 들어옴 ~~~~~~~~~~~~");
		return ysyUserMngReadService.getUserList();
	}
	
	
	
	
}
