package com.ysy;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.jwt.auth.dto.UserInfoDto;
import com.ysy.jwt.auth.entity.YsyUserAddress;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.repository.YsyUserAddressRepository;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Jpa_1_N_test {

	@Autowired
	private YsyUserMstRepository ysyUserMstRepository;
	@Autowired
	private YsyUserAddressRepository ysyUserAddressRepository;
	
	@Test
	@Transactional
	public void getUserList() {
		
		
//		
		
		List<YsyUserAddress> listt = ysyUserAddressRepository.getUserInfoJPQL("clubbboy@naver.com");
		UserInfoDto dto1 = new UserInfoDto(listt);
		
		
		
		
		
		
		YsyUserMst user = ysyUserMstRepository.findById("clubbboy@naver.com1")
				.orElseThrow(()->  new IllegalArgumentException("except test"));//
		
		List<YsyUserAddress> tmpAddrList = ysyUserAddressRepository.findByYsyUserMst(user);
		UserInfoDto dto2 = new UserInfoDto(tmpAddrList);
		
		System.out.println("");
	}
}
