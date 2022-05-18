package com.ysy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.common.SysEnum;
import com.ysy.common.SysEnum.enumGrps;
import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;
import com.ysy.jwt.auth.repository.YsyBizMstRepository;
import com.ysy.jwt.auth.repository.YsyGrpMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupDummy {
	
	@Autowired
	YsyGrpMstRepository ysyGrpRepository;
	@Autowired
	YsyBizMstRepository ysyBizRepository;
	
	@Test
	public void addGroup() {
		System.out.println("group dummy sample data start =========================");
		
		List<YsyBizMst> bizTemp = ysyBizRepository.findAll();
		List<enumGrps> enumTemp = Stream.of(SysEnum.enumGrps.values()).collect(Collectors.toList());
		
		int enumRoleNum;
		
		for(YsyBizMst ysyBizMst : bizTemp) {
			enumRoleNum = 1;
			String bizCd = ysyBizMst.getBizCd();
			System.out.println(bizCd);
			
			for(enumGrps enumRole : enumTemp) {
				System.out.println(enumRole);
				GrpPK grpPK = new GrpPK(enumRole, bizCd);
				YsyGrpMst grpData = YsyGrpMst.builder()
						.grpPK(grpPK)
						.ysyBizMst(ysyBizMst)
						.grpNm("")
						.useYn("Y")
						.levelId(enumRoleNum)
						.build();
				
				ysyGrpRepository.save(grpData);
				enumRoleNum++;
			}
		}
		System.out.println("group dummy sample data end =========================");
	}
}
