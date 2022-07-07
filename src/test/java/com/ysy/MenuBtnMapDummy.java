package com.ysy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.biz.entity.QStoreNotice;
import com.ysy.jwt.auth.entity.QYsyBtnMst;
import com.ysy.jwt.auth.entity.QYsyGrpMenuMap;
import com.ysy.jwt.auth.entity.QYsyGrpMst;
import com.ysy.jwt.auth.entity.QYsyMenuBtnMap;
import com.ysy.jwt.auth.entity.QYsyMenuExptMst;
import com.ysy.jwt.auth.entity.QYsyMenuMst;
import com.ysy.jwt.auth.entity.QYsyUserAddress;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyBtnMst;
import com.ysy.jwt.auth.entity.YsyMenuMst;
@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuBtnMapDummy {
	
	@PersistenceContext
	EntityManager em; // 1

	@Test
	@Transactional
	@Rollback(false)
	public void setDummyData() {
		System.out.println("menu btn map data start =========================");

		JPAQueryFactory           query = new JPAQueryFactory(em); 
		QStoreNotice       qStoreNotice = QStoreNotice.storeNotice;
		QYsyGrpMenuMap   qYsyGrpMenuMap = QYsyGrpMenuMap.ysyGrpMenuMap;
		QYsyUserMst         qYsyUserMst = QYsyUserMst.ysyUserMst;
		
		
		QYsyGrpMst           qYsyGrpMst = QYsyGrpMst.ysyGrpMst;
		QYsyMenuExptMst qYsyMenuExptMst = QYsyMenuExptMst.ysyMenuExptMst;
		QYsyUserAddress qYsyUserAddress = QYsyUserAddress.ysyUserAddress;
		
		QYsyMenuMst         qYsyMenuMst = QYsyMenuMst.ysyMenuMst;
		QYsyBtnMst           qYsyBtnMst = QYsyBtnMst.ysyBtnMst;
		QYsyMenuBtnMap   qYsyMenuBtnMap = QYsyMenuBtnMap.ysyMenuBtnMap;
	
		List<YsyMenuMst> menuList = query
				.select(qYsyMenuMst)
				.from(qYsyMenuMst)
				.where(qYsyMenuMst.pMenuId.ne("").or(qYsyMenuMst.pMenuId.isEmpty()))
				.fetch();
		
		List<YsyBtnMst> btnList = query
				.select(qYsyBtnMst)
				.from(qYsyBtnMst)
				.orderBy(qYsyBtnMst.btnSort.asc())
				.fetch();
		

		System.out.println("menuid ="+menuList.get(0).getMenuId());
		System.out.println("btnId =" +btnList.get(0).getBtnId());
		
//		SQLInsertClause aa;
//		SQLInsertClause myInsert = query.insert(qYsyMenuBtnMap);
		for(YsyMenuMst menu : menuList) {
			String menuId =menu.getMenuId();
			for(YsyBtnMst btn : btnList) {
				em.createNativeQuery("INSERT INTO ysy_menu_btn_map (menu_id , btn_id , reg_dt,reg_id) VALUES (?,?,now(),'admin')")
			      .setParameter(1, menuId)
			      .setParameter(2, btn.getBtnId())
			      .executeUpdate();
			}
		}
		
//		query.insert(qYsyMenuBtnMap)
//		.columns(qYsyMenuBtnMap.ysyMenuMst.menuId , qYsyMenuBtnMap.ysyBtnMst.btnId)
//		.values("menu_2022-05-30T08:45:21.804" , "DELETE")
//		.execute()
;		
		
		
		
		System.out.println("menu btn map data end =========================");
	}
}
