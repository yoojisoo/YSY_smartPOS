package com.ysy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.common.YsyUtil;
import com.ysy.jwt.auth.entity.YsyMenuMst;
import com.ysy.jwt.auth.repository.YsyMenuMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuDummy {
	
	@Autowired
	private YsyMenuMstRepository ysyMenuMstRepository;
	
	
	@Autowired
	private YsyUtil ysyUtil;
	
	@Test
	public void addGroup() {
		System.out.println("menu dummy sample data start =========================");
		
		/** admin parent menu dummy  */
		List<String> adminNmList = Arrays.asList("사용자관리", "메뉴관리", "정산관리", "예약관리", "매장분석", "상품관리");
		for (int i = 0; i < adminNmList.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
							.menuId(ysyUtil.createDbKey("menu"))
							.isAdmin("Y")
							.menuNm(adminNmList.get(i))
							.menuPath("")
							.menuSeq(i)
							.pMenuId("")
							.useYn("Y")
							.menuFullPath("")
							.useYn("Y")
							.build();
			System.out.println(ysyMenuMst);
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		
		List<YsyMenuMst> menuList = new ArrayList<YsyMenuMst>();
		menuList = ysyMenuMstRepository.findAll();
		List<YsyMenuMst> pMenu1 = menuList.stream().filter(x->x.getMenuNm().equals("사용자관리")).collect(Collectors.toList());
		String adminPId1 = pMenu1.get(0).getMenuId();
		
		/** admin-사용자관리 child menu dummy  */
		List<String> adminChildList = Arrays.asList("사용자 관리", "메뉴 권한 관리", "메뉴 관리", "메뉴 버튼 관리");
		for (int i = 0; i < adminChildList.size(); i++) {
			
			int seq = i+10;
			
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
							.menuId(ysyUtil.createDbKey("menu"))
							.isAdmin("Y")
							.menuNm(adminChildList.get(i))
							.menuPath("")
							.menuSeq(seq)
							.pMenuId(adminPId1)
							.useYn("Y")
							.menuFullPath("")
							.useYn("Y")
							.build();
			
			ysyMenuMstRepository.save(ysyMenuMst);
		}

		
		/** user parent menu dummy  */
		List<String> userNmList = Arrays.asList("공지사항", "매장현황", "배달현황");
		for (int i = 0; i < userNmList.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuId(ysyUtil.createDbKey("menu"))
					.isAdmin("N")
					.menuNm(userNmList.get(i))
					.menuPath("")
					.menuSeq(i)
					.pMenuId("")
					.useYn("Y")
					.menuFullPath("")
					.useYn("Y")
					.build();
			System.out.println(ysyMenuMst);
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		List<YsyMenuMst> menuList2 = new ArrayList<YsyMenuMst>();
		menuList2 = ysyMenuMstRepository.findAll();
		List<YsyMenuMst> pMenu2 = menuList2.stream().filter(x->x.getMenuNm().equals("공지사항")).collect(Collectors.toList());
		String userPId = pMenu2.get(0).getMenuId();
		
		/** user child menu dummy  */
		List<String> userChildList = Arrays.asList("시스템 공지사항", "Store 공지사항", "QnA");
		for (int i = 0; i < userChildList.size(); i++) {
			
			int seq = i+10;
			
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
							.menuId(ysyUtil.createDbKey("menu"))
							.isAdmin("N")
							.menuNm(userChildList.get(i))
							.menuPath("")
							.menuSeq(seq)
							.pMenuId(userPId)
							.useYn("Y")
							.menuFullPath("")
							.useYn("Y")
							.build();
			
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		
		System.out.println("menu dummy sample data end =========================");
	}
}
