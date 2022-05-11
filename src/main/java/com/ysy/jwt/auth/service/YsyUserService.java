package com.ysy.jwt.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysy.jwt.auth.entity.YsyUser;
import com.ysy.jwt.auth.repository.YsyUserRepository;

@Service
public class YsyUserService {

	@Autowired
	private YsyUserRepository ysyUserRepository;
	
	
	/** user 등록 */
	public boolean signUp(YsyUser ysyUser ) {
		
		if(ysyUserRepository.save(ysyUser)  == null) 
			return false;
		
		return true;
	}
	
	/** user 존재여부 확인 존재 : true */
	public boolean isUser(String username) {
	
		if(ysyUserRepository.findByUsername(username) == null)
			return false;
		
		return true;
	}
}
