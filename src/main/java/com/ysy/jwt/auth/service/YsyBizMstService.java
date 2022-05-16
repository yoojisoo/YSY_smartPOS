package com.ysy.jwt.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.repository.YsyBizMstRepository;

@Service
public class YsyBizMstService {

	@Autowired
	private YsyBizMstRepository ysyBizRepository;
	
	
	/** biz 등록 */
	public boolean createBiz(YsyBizMst ysyBiz ) {
		
		if(ysyBizRepository.save(ysyBiz)  == null) 
			return false;
		
		return true;
	}
	
	/** biz 존재여부 확인 존재 : true */
	public boolean isBizCd(String bizCd) {
	
		if(ysyBizRepository.findByBizCd(bizCd) == null)
			return false;
		
		return true;
	}
	public YsyBizMst getBizData(String bizCd) {
		
		return ysyBizRepository.findByBizCd(bizCd);
			
	}
}
