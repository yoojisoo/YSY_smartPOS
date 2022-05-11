package com.ysy.jwt.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysy.jwt.auth.entity.YsyBiz;
import com.ysy.jwt.auth.repository.YsyBizRepository;

@Service
public class YsyBizService {

	@Autowired
	private YsyBizRepository ysyBizRepository;
	
	
	/** biz 등록 */
	public boolean createBiz(YsyBiz ysyBiz ) {
		
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
}
