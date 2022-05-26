package com.ysy;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.repository.YsyGrpMstRepository;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDummy {
	
	@Autowired
	private YsyUserMstRepository ysyUserMstRepository;
	
	@Autowired
	private YsyGrpMstRepository ysyGrpMstRepository;

	
	@Test
	@Transactional
	public void addUser() {
//		List<YsyBizMst> bizList = ysyBizMstRepository.findAll();
		
		System.out.println("UserDummy -> addUser");
		System.out.println("user dummy sample data start");
		List<YsyGrpMst> grpList = ysyGrpMstRepository.findAll();
		System.out.println(grpList.get(0).getGrpPK().getBizCd() + ", " + grpList.get(0).getGrpPK().getGrpId());
		
		//username = ID = email
		YsyUserMst ysyUserMst = YsyUserMst.builder()
										  .username("yoojisoo@naver.com")
										  .password("pass7521")
										  .name("유지수")
										  .phone("01079977521")
										  .ysyGrpMst(grpList.get(0))
										  .build();
					  ysyUserMstRepository.save(ysyUserMst);
					  System.out.println("유저마스터 더미 데이터 끝");
				

		
	}
}
