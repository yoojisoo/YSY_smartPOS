package com.ysy.biz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.biz.dto.ResponseDto;
import com.ysy.biz.service.SystemNoticeService;

@RestController
@CrossOrigin 
@RequestMapping("/ysy/v1") 
public class SystemNoticeController {
	
	@Autowired
	SystemNoticeService systemNoticeService;

	
	/** 2022 06 09 yoojisoo  */
	@GetMapping("/findSystemNotice") 
	public ResponseDto<?> SystemNotice(@RequestParam int size){
		if(size <= 0) size = 5;
		return systemNoticeService.findSystemNotice(size);
	}
	
	
	
}
