package com.ysy.biz.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.biz.entity.SystemNotice;
import com.ysy.biz.service.SystemNoticeService;

@RestController
@CrossOrigin
@RequestMapping("/ysy/v1")
public class SystemNoticeController {
	
	@Autowired
	SystemNoticeService systemNoticeService;

	
	/** 2022 06 09 yoojisoo  */
	@GetMapping("/getSystemNotice")
	public List<SystemNotice> getSystemNotice(){
		System.out.println("공지사항 가져가쟈~~~~");

		return systemNoticeService.getSystemNotice();
	}
	
	
	
}
