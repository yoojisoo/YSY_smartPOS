package com.ysy;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.repository.YsyBizMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BizDummy {
	
	@Autowired
	YsyBizMstRepository ysyBizRepository;
	
	@Test
	public void addBiz() {
		System.out.println("biz dummy sample data start =========================");
		
		YsyBizMst bizData1 = YsyBizMst.builder()
				.bizCd("0001")
				.bizNm("bizNm_0001")
				.useYn("Y")
				.delYn("N")
				.build();
		
		YsyBizMst bizData2 = YsyBizMst.builder()
				.bizCd("0002")
				.bizNm("bizNm_0002")
				.useYn("Y")
				.delYn("N")
				.build();
		
		YsyBizMst bizData3 = YsyBizMst.builder()
				.bizCd("0003")
				.bizNm("bizNm_0003")
				.useYn("Y")
				.delYn("N")
				.build();
		
		ysyBizRepository.save(bizData1);
		ysyBizRepository.save(bizData2);
		ysyBizRepository.save(bizData3);
		
		System.out.println("biz dummy sample data end =========================");
	}
}
