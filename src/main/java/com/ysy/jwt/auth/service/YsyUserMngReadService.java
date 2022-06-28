package com.ysy.jwt.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysy.jwt.auth.dto.UserDto;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;

@Service
public class YsyUserMngReadService {

	@Autowired
	private YsyUserMstRepository ysyUserRepository;
	
	/** grid에서 get user info */
	public List<UserDto> getUserList() {
//		List<YsyUserMst> userlist = ysyUserRepository.findAll();
		List<Object[]> resultList = ysyUserRepository.getDefaultUserList();
		
		List<UserDto> userList = resultList.stream()
										  .map(x -> UserDto.builder()
												  .user_id((String)x[0])
												  .user_phone((String)x[1])
												  .user_nm((String)x[2])
												  .reg_dt((String)x[3])
												  .grp_id((String)x[6])
										  		  .build()
										  	   )
										  .collect(Collectors.toList());
		UserDto userDto = new UserDto();
		userDto.setObj(resultList);
		return userList;
	}
}
