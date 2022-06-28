package com.ysy.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysy.biz.entity.StoreNotice;
import com.ysy.biz.repository.StoreNoticeRepository;

@Service
//@AllArgsConstructor
public class StoreNoticeService {

	@Autowired
	private StoreNoticeRepository storeNoticeRepository;
	
	
	/** biz 등록 */
	public List<StoreNotice> getStoreNotice() {
		
		List<StoreNotice> noticeList = storeNoticeRepository.findAll();
		
		return noticeList;
	}
	

}
