package com.ysy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.jwt.auth.common.SysEnum;
import com.ysy.jwt.auth.common.SysEnum.enumGrps;
import com.ysy.jwt.auth.entity.YsyBizMst;
import com.ysy.jwt.auth.entity.YsyGrpMst;
import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;
import com.ysy.jwt.auth.repository.YsyBizMstRepository;
import com.ysy.jwt.auth.repository.YsyGrpMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Ysy2GroupBaseDataCreate {
	
	@Autowired
	YsyGrpMstRepository ysyGrpRepository;
	@Autowired
	YsyBizMstRepository ysyBizRepository;
	
	@Test
	public void addGroup() {
		System.out.println("group dummy sample data start =========================");
		
		List<YsyBizMst> bizTemp = ysyBizRepository.findAll();
		List<enumGrps> enumTemp = Stream.of(SysEnum.enumGrps.values()).collect(Collectors.toList());
		List<Integer> enumRoleNumList = Arrays.asList(9999, 1000, 100, 10, 0);
		
		int enumRoleNum;
		
		for(YsyBizMst ysyBizMst : bizTemp) {
			String bizCd = ysyBizMst.getBizCd();
			
			for(enumGrps enumRole : enumTemp) {
				
				if     (enumRole.toString().equals("DEFAULT_USER"))       enumRoleNum = enumRoleNumList.get(0);
				else if(enumRole.toString().equals("ROLE_TEMP_USER"))     enumRoleNum = enumRoleNumList.get(1);
				else if(enumRole.toString().equals("ROLE_USER"))          enumRoleNum = enumRoleNumList.get(1)+1000;
				else if(enumRole.toString().equals("ROLE_USER_VIP1"))     enumRoleNum = enumRoleNumList.get(2);
				else if(enumRole.toString().equals("ROLE_USER_VIP2"))     enumRoleNum = enumRoleNumList.get(2)+100;
				else if(enumRole.toString().equals("ROLE_USER_VIP3"))     enumRoleNum = enumRoleNumList.get(2)+200;
				else if(enumRole.toString().equals("ROLE_MANAGER"))       enumRoleNum = enumRoleNumList.get(3);
				else if(enumRole.toString().equals("ROLE_MANAGER_SUPER")) enumRoleNum = enumRoleNumList.get(3)+10;
				else enumRoleNum = enumRoleNumList.get(4);
				
				GrpPK grpPK = new GrpPK(enumRole, bizCd);
				YsyGrpMst grpData = YsyGrpMst.builder()
						.grpPK(grpPK)
						.ysyBizMst(ysyBizMst)
						.grpNm("")
						.useYn("Y")
						.levelId(enumRoleNum)
						.build();
				
				ysyGrpRepository.save(grpData);
			}
		}
		System.out.println("group dummy sample data end =========================");
	}
}
