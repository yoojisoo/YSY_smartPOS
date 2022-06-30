package com.ysy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.biz.entity.QStoreNotice;
import com.ysy.biz.entity.StoreNotice;
import com.ysy.jwt.auth.dto.UserInfoDto;
import com.ysy.jwt.auth.entity.QYsyUserAddress;
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
	
	@PersistenceContext
	EntityManager em; // 1
	
	
	@Test
	@Transactional
	public void getUserList() {
		
		
		JPAQueryFactory query = new JPAQueryFactory(em); // 2
		QStoreNotice qStoreNotice = QStoreNotice.storeNotice;
		List<StoreNotice> result = query
				.select(qStoreNotice)
				.from(qStoreNotice)
				.where(qStoreNotice.ysyUserMst.username.eq("clubbboy@naver.com"))
				.fetch();

		
		QYsyUserAddress qYsyUserAddress = QYsyUserAddress.ysyUserAddress;
		
		List<YsyUserAddress> result2 = query
				.select(qYsyUserAddress)
				.from(qYsyUserAddress)
				.where(qYsyUserAddress.ysyUserMst.username.eq("clubbboy@naver.com"))
				.fetch();
//		List<YsyUserAddress> listt = ysyUserAddressRepository.getUserInfoJPQL("clubbboy@naver.com");
		UserInfoDto dto1 = new UserInfoDto(result2);
		
		
		
		
		
		
		YsyUserMst user = ysyUserMstRepository.findById("clubbboy@naver.com")
				.orElseThrow(()->  new IllegalArgumentException("except test"));//
		
		List<YsyUserAddress> tmpAddrList = ysyUserAddressRepository.findByYsyUserMst(user);
		UserInfoDto dto2 = new UserInfoDto(tmpAddrList);
		
		System.out.println("");
	}
}
