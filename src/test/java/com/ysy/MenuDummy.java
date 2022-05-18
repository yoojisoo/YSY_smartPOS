package com.ysy;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
import com.ysy.jwt.auth.entity.YsyMenuMst;
import com.ysy.jwt.auth.repository.YsyMenuMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuDummy {
	
	@Autowired
	private YsyMenuMstRepository ysyMenuMstRepository;
	
	@Test
	public void addGroup() {
		System.out.println("group dummy sample data start =========================");
		
		/** admin parent menu dummy  */
		List<String> name = Arrays.asList("사용자관리","상품관리","정산관리","예약관리","매장분석");
		for (int i = 0; i < 5; i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuId("menu00"+(i+1))
					.isAdmin("Y")
					.menuNm(name.get(i))
					.menuPath("")
					.menuSeq(i)
					.pMenuId(null)
					.useYn("Y")
					.build();
			
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		/** admin parent menu dummy  */
		List<String> childname = Arrays.asList("사용자관리","메뉴관리","그룹관리","메뉴버튼관리");
		for (int i = 0; i < 4; i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuId("menu001_menu00"+(i+1))
					.isAdmin("Y")
					.menuNm(name.get(i))
					.menuPath("")
					.menuSeq(i)
					.pMenuId("menu001")
					.useYn("Y")
					.build();
			
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		
//		List<YsyBizMst> bizTemp = ysyBizRepository.findAll();
//		List<enumGrps> enumTemp = Stream.of(SysEnum.enumGrps.values()).collect(Collectors.toList());
//		
//		int enumRoleNum;
//		
//		for(YsyBizMst ysyBizMst : bizTemp) {
//			enumRoleNum = 1;
//			String bizCd = ysyBizMst.getBizCd();
//			System.out.println(bizCd);
//			
//			for(enumGrps enumRole : enumTemp) {
//				System.out.println(enumRole);
//				GrpPK grpPK = new GrpPK(enumRole, bizCd);
//				YsyGrpMst grpData = YsyGrpMst.builder()
//						.grpPK(grpPK)
//						.ysyBizMst(ysyBizMst)
//						.grpNm("")
//						.useYn("Y")
//						.levelId(enumRoleNum)
//						.build();
//				
//				ysyGrpRepository.save(grpData);
//				enumRoleNum++;
//			}
//		}
		System.out.println("group dummy sample data end =========================");
	}
}
