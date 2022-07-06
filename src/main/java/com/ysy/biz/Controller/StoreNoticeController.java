package com.ysy.biz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.biz.dto.ResponseDto;
import com.ysy.biz.service.StoreNoticeService;

@RestController
@CrossOrigin
@RequestMapping("/ysy/v1")
public class StoreNoticeController {
	
	@Autowired
	StoreNoticeService storeNoticeService;
	
	/** 2022 06 28 mnew2m */
	@GetMapping("/findStoreNotice")
	public ResponseDto<?> findStoreNotice(@RequestParam int size){
		if(size <= 0) size = 5;
		return storeNoticeService.findStoreNotice(size);
	}
}
