package com.ysy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.jwt.auth.common.YsyUtil;
import com.ysy.jwt.auth.entity.YsyMenuMst;
import com.ysy.jwt.auth.repository.YsyMenuMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Ysy3MenuBaseDataCreate {
	
	@Autowired
	private YsyMenuMstRepository ysyMenuMstRepository;
	
	
	@Autowired
	private YsyUtil ysyUtil;
	
	@Test
	public void addMenu() {
		System.out.println("menu dummy sample data start =========================");
		
		String useY    = "Y";
		String isAdminY = "Y";
		String isAdminN = "N";
		
		List<String> pMenuNmList  = Arrays.asList("커뮤니티", "예약관리", "배달관리", "매장관리", "상품관리", "정산현황", "사용자관리", "메뉴관리");
		List<String> pIsAdminList = Arrays.asList(isAdminN	  , isAdminN	 , isAdminN  , isAdminY  , isAdminY  , isAdminY  , isAdminY    , isAdminY);
		List<String> pMenuIdList  = new ArrayList<String>();
		int menuSeq = 0;
		for(int i = 0; i < pMenuNmList.size(); i++) {
			pMenuIdList.add(ysyUtil.createUUID("menu").toString());
			
			// 상위메뉴 Insert
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuSeq(menuSeq)
					.pMenuId("")
					.menuId(pMenuIdList.get(i))
					.isAdmin(pIsAdminList.get(i))
					.menuNm(pMenuNmList.get(i))
					.menuFullPath("")
					.menuPath("")
					.useYn(useY)
					.menuIcon("")
					.build();
			ysyMenuMstRepository.save(ysyMenuMst);
			menuSeq+=10;
		}
		
		System.out.println();
		
		/** 커뮤니티 */
		YsyMenuMst community = ysyMenuMstRepository.findAll()
								.stream()
								.filter(x->x.getMenuId().contains("menu-")
										&& x.getMenuNm().equals("커뮤니티"))
								.collect(Collectors.toList()).get(0);
		Map<Integer, List<String>> communityC = new HashMap<Integer, List<String>>(){
			private static final long serialVersionUID = 1L;
			{
				put(community.getMenuSeq()+1, Arrays.asList("시스템공지사항", "/noticeSystem", "mdi-home"));
				put(community.getMenuSeq()+2, Arrays.asList("스토어공지사항", "/noticeStore" , "mdi-home"));
				put(community.getMenuSeq()+3, Arrays.asList("QnA"			  , "/qna"		   , "mdi-comment-question-outline"));
			}
		};
		
		for(int i = 1; i <= communityC.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuSeq(community.getMenuSeq()+i)
					.pMenuId(community.getMenuId())
					.menuId(ysyUtil.createUUID("menu").toString())
					.isAdmin(community.getIsAdmin())
					.menuNm(communityC.get(community.getMenuSeq()+i).get(0))
					.menuFullPath("")
					.menuPath(communityC.get(community.getMenuSeq()+i).get(1))
					.useYn(useY)
					.menuIcon(communityC.get(community.getMenuSeq()+i).get(2))
					.build();
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		System.out.println();
		
		/** 예약관리 */
		YsyMenuMst reservation = ysyMenuMstRepository.findAll()
									.stream()
									.filter(x->x.getMenuId().contains("menu-")
											&& x.getMenuNm().equals("예약관리"))
									.collect(Collectors.toList()).get(0);
		Map<Integer, List<String>> reservationC = new HashMap<Integer, List<String>>(){
			private static final long serialVersionUID = 1L;
			{
				put(reservation.getMenuSeq()+1, Arrays.asList("예약현황", "/reservationStatus" , "mdi-home"));
				put(reservation.getMenuSeq()+2, Arrays.asList("예약내역", "/reservationHistory", "mdi-home"));
			}
		};
		
		for(int i = 1; i <= reservationC.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuSeq(reservation.getMenuSeq()+i)
					.pMenuId(reservation.getMenuId())
					.menuId(ysyUtil.createUUID("menu").toString())
					.isAdmin(reservation.getIsAdmin())
					.menuNm(reservationC.get(reservation.getMenuSeq()+i).get(0))
					.menuFullPath("")
					.menuPath(reservationC.get(reservation.getMenuSeq()+i).get(1))
					.useYn(useY)
					.menuIcon(reservationC.get(reservation.getMenuSeq()+i).get(2))
					.build();
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		System.out.println();
		
		/** 배달관리 */
		YsyMenuMst delivery = ysyMenuMstRepository.findAll()
								.stream()
								.filter(x->x.getMenuId().contains("menu-")
										&& x.getMenuNm().equals("배달관리"))
								.collect(Collectors.toList()).get(0);
		Map<Integer, List<String>> deliveryC = new HashMap<Integer, List<String>>(){
			private static final long serialVersionUID = 1L;
			{
				put(delivery.getMenuSeq()+1, Arrays.asList("배달현황", "/deliveryStatus" , "mdi-home"));
				put(delivery.getMenuSeq()+2, Arrays.asList("배달내역", "/deliveryHistory", "mdi-home"));
			}
		};
		
		for(int i = 1; i <= deliveryC.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuSeq(delivery.getMenuSeq()+i)
					.pMenuId(delivery.getMenuId())
					.menuId(ysyUtil.createUUID("menu").toString())
					.isAdmin(delivery.getIsAdmin())
					.menuNm(deliveryC.get(delivery.getMenuSeq()+i).get(0))
					.menuFullPath("")
					.menuPath(deliveryC.get(delivery.getMenuSeq()+i).get(1))
					.useYn(useY)
					.menuIcon(deliveryC.get(delivery.getMenuSeq()+i).get(2))
					.build();
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		System.out.println();
		
		/** 매장관리 */
		YsyMenuMst store = ysyMenuMstRepository.findAll()
								.stream()
								.filter(x->x.getMenuId().contains("menu-")
										&& x.getMenuNm().equals("매장관리"))
								.collect(Collectors.toList()).get(0);
		Map<Integer, List<String>> storeC = new HashMap<Integer, List<String>>(){
			private static final long serialVersionUID = 1L;
			{
				put(store.getMenuSeq()+1, Arrays.asList("매장관리", "/storeMng", "mdi-home"));
			}
		};
		
		for(int i = 1; i <= storeC.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuSeq(store.getMenuSeq()+i)
					.pMenuId(store.getMenuId())
					.menuId(ysyUtil.createUUID("menu").toString())
					.isAdmin(store.getIsAdmin())
					.menuNm(storeC.get(store.getMenuSeq()+i).get(0))
					.menuFullPath("")
					.menuPath(storeC.get(store.getMenuSeq()+i).get(1))
					.useYn(useY)
					.menuIcon(storeC.get(store.getMenuSeq()+i).get(2))
					.build();
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		System.out.println();
		
		/** 상품관리 */
		YsyMenuMst prod = ysyMenuMstRepository.findAll()
						.stream()
						.filter(x->x.getMenuId().contains("menu-")
								&& x.getMenuNm().equals("상품관리"))
						.collect(Collectors.toList()).get(0);
		Map<Integer, List<String>> prodC = new HashMap<Integer, List<String>>(){
			private static final long serialVersionUID = 1L;
			{
				put(prod.getMenuSeq()+1, Arrays.asList("상품관리", "/prodMng" , "mdi-home"));
			}
		};
		
		for(int i = 1; i <= prodC.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuSeq(prod.getMenuSeq()+i)
					.pMenuId(prod.getMenuId())
					.menuId(ysyUtil.createUUID("menu").toString())
					.isAdmin(prod.getIsAdmin())
					.menuNm(prodC.get(prod.getMenuSeq()+i).get(0))
					.menuFullPath("")
					.menuPath(prodC.get(prod.getMenuSeq()+i).get(1))
					.useYn(useY)
					.menuIcon(prodC.get(prod.getMenuSeq()+i).get(2))
					.build();
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		System.out.println();
		
		/** 정산현황 */
		YsyMenuMst calculate = ysyMenuMstRepository.findAll()
						.stream()
						.filter(x->x.getMenuId().contains("menu-")
								&& x.getMenuNm().equals("정산현황"))
						.collect(Collectors.toList()).get(0);
		Map<Integer, List<String>> calculateC = new HashMap<Integer, List<String>>(){
			private static final long serialVersionUID = 1L;
			{
				put(calculate.getMenuSeq()+1, Arrays.asList("일일정산", "/dailyCalculate"  , "mdi-home"));
				put(calculate.getMenuSeq()+2, Arrays.asList("월별정산", "/monthlyCalculate", "mdi-home"));
				put(calculate.getMenuSeq()+3, Arrays.asList("연별정산", "/yearlyCalculate" , "mdi-home"));
			}
		};
		
		for(int i = 1; i <= calculateC.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuSeq(calculate.getMenuSeq()+i)
					.pMenuId(calculate.getMenuId())
					.menuId(ysyUtil.createUUID("menu").toString())
					.isAdmin(calculate.getIsAdmin())
					.menuNm(calculateC.get(calculate.getMenuSeq()+i).get(0))
					.menuFullPath("")
					.menuPath(calculateC.get(calculate.getMenuSeq()+i).get(1))
					.useYn(useY)
					.menuIcon(calculateC.get(calculate.getMenuSeq()+i).get(2))
					.build();
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		System.out.println();
		
		/** 사용자관리 */
		YsyMenuMst user = ysyMenuMstRepository.findAll()
						.stream()
						.filter(x->x.getMenuId().contains("menu-")
								&& x.getMenuNm().equals("사용자관리"))
						.collect(Collectors.toList()).get(0);
		Map<Integer, List<String>> userC = new HashMap<Integer, List<String>>(){
			private static final long serialVersionUID = 1L;
			{
				put(user.getMenuSeq()+1, Arrays.asList("사용자관리", "/userMng", "mdi-home"));
			}
		};
		
		for(int i = 1; i <= userC.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuSeq(user.getMenuSeq()+i)
					.pMenuId(user.getMenuId())
					.menuId(ysyUtil.createUUID("menu").toString())
					.isAdmin(user.getIsAdmin())
					.menuNm(userC.get(user.getMenuSeq()+i).get(0))
					.menuFullPath("")
					.menuPath(userC.get(user.getMenuSeq()+i).get(1))
					.useYn(useY)
					.menuIcon(userC.get(user.getMenuSeq()+i).get(2))
					.build();
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		System.out.println();
		
		/** 메뉴관리 */
		YsyMenuMst menu = ysyMenuMstRepository.findAll()
						.stream()
						.filter(x->x.getMenuId().contains("menu-")
								&& x.getMenuNm().equals("메뉴관리"))
						.collect(Collectors.toList()).get(0);
		Map<Integer, List<String>> menuC = new HashMap<Integer, List<String>>(){
			private static final long serialVersionUID = 1L;
			{
				put(menu.getMenuSeq()+1, Arrays.asList("메뉴권한관리", "/menuRoleMng", "mdi-home"));
				put(menu.getMenuSeq()+2, Arrays.asList("메뉴버튼관리", "/menuBtnMng" , "mdi-home"));
			}
		};
		
		for(int i = 1; i <= menuC.size(); i++) {
			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
					.menuSeq(menu.getMenuSeq()+i)
					.pMenuId(menu.getMenuId())
					.menuId(ysyUtil.createUUID("menu").toString())
					.isAdmin(menu.getIsAdmin())
					.menuNm(menuC.get(menu.getMenuSeq()+i).get(0))
					.menuFullPath("")
					.menuPath(menuC.get(menu.getMenuSeq()+i).get(1))
					.useYn(useY)
					.menuIcon(menuC.get(menu.getMenuSeq()+i).get(2))
					.build();
			ysyMenuMstRepository.save(ysyMenuMst);
		}
		
		System.out.println();
		
//		/** admin parent menu dummy  */
//		List<String> adminNmList = Arrays.asList("사용자관리", "메뉴관리", "정산관리", "예약관리", "매장분석", "상품관리");
//		for (int i = 0; i < adminNmList.size(); i++) {
//			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
//							.menuId(ysyUtil.createDbKey("menu"))
//							.isAdmin("Y")
//							.menuNm(adminNmList.get(i))
//							.menuPath("")
//							.menuSeq(i)
//							.pMenuId("")
//							.useYn("Y")
//							.menuFullPath("")
//							.useYn("Y")
//							.build();
//			System.out.println(ysyMenuMst);
//			ysyMenuMstRepository.save(ysyMenuMst);
//		}
//		
//		
//		List<YsyMenuMst> menuList = new ArrayList<YsyMenuMst>();
//		menuList = ysyMenuMstRepository.findAll();
//		List<YsyMenuMst> pMenu1 = menuList.stream().filter(x->x.getMenuNm().equals("사용자관리")).collect(Collectors.toList());
//		String adminPId1 = pMenu1.get(0).getMenuId();
//		
//		/** admin-사용자관리 child menu dummy  */
//		List<String> adminChildList = Arrays.asList("사용자 관리", "메뉴 권한 관리", "메뉴 관리", "메뉴 버튼 관리");
//		for (int i = 0; i < adminChildList.size(); i++) {
//			
//			int seq = i+10;
//			
//			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
//							.menuId(ysyUtil.createDbKey("menu"))
//							.isAdmin("Y")
//							.menuNm(adminChildList.get(i))
//							.menuPath("")
//							.menuSeq(seq)
//							.pMenuId(adminPId1)
//							.useYn("Y")
//							.menuFullPath("")
//							.useYn("Y")
//							.build();
//			
//			ysyMenuMstRepository.save(ysyMenuMst);
//		}
//
//		
//		/** user parent menu dummy  */
//		List<String> userNmList = Arrays.asList("공지사항", "매장현황", "배달현황");
//		for (int i = 0; i < userNmList.size(); i++) {
//			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
//					.menuId(ysyUtil.createDbKey("menu"))
//					.isAdmin("N")
//					.menuNm(userNmList.get(i))
//					.menuPath("")
//					.menuSeq(i)
//					.pMenuId("")
//					.useYn("Y")
//					.menuFullPath("")
//					.useYn("Y")
//					.build();
//			System.out.println(ysyMenuMst);
//			ysyMenuMstRepository.save(ysyMenuMst);
//		}
//		
//		List<YsyMenuMst> menuList2 = new ArrayList<YsyMenuMst>();
//		menuList2 = ysyMenuMstRepository.findAll();
//		List<YsyMenuMst> pMenu2 = menuList2.stream().filter(x->x.getMenuNm().equals("공지사항")).collect(Collectors.toList());
//		String userPId = pMenu2.get(0).getMenuId();
//		
//		/** user child menu dummy  */
//		List<String> userChildList = Arrays.asList("시스템 공지사항", "Store 공지사항", "QnA");
//		for (int i = 0; i < userChildList.size(); i++) {
//			
//			int seq = i+10;
//			
//			YsyMenuMst ysyMenuMst = YsyMenuMst.builder()
//							.menuId(ysyUtil.createDbKey("menu"))
//							.isAdmin("N")
//							.menuNm(userChildList.get(i))
//							.menuPath("")
//							.menuSeq(seq)
//							.pMenuId(userPId)
//							.useYn("Y")
//							.menuFullPath("")
//							.useYn("Y")
//							.build();
//			
//			ysyMenuMstRepository.save(ysyMenuMst);
//		}
		
		
		System.out.println("menu dummy sample data end =========================");
	}
}
