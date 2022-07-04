package com.ysy.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysy.biz.entity.SystemNotice;
import com.ysy.biz.repository.SystemNoticeRepository;

@Service
//@AllArgsConstructor
public class SystemNoticeService {

	@Autowired
	private SystemNoticeRepository systemNoticeRepository;
	
	
	/** biz 등록 */
	public List<SystemNotice> findSystemNotice() {
		
		List<SystemNotice> noticeList = systemNoticeRepository.findAll();
		
		return noticeList;
	}
	

}
