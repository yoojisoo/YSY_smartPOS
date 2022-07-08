package com.ysy;

import java.time.LocalDateTime;
import java.util.Calendar;

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
import com.ysy.jwt.auth.entity.base.YsyCalendarMst.CalPK;
/**
 * 
 * @author clubbboy@naver.com
 * 
 * calendar 만듬
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Ysy6CalendarBaseDataCreate {
	
	@PersistenceContext
	EntityManager em; // 1

	@Test
	@Transactional
	@Rollback(false)
	public void setDummyData() {
		System.out.println("calendar create start =========================");

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
	
		
		
		int year = LocalDateTime.now().getYear();
		int month = LocalDateTime.now().getMonth().getValue();   
		
		int minYear = 2019;
		int maxYear = 2032;
		int minMonth = 1;
		int maxMonth = 12;
		boolean isFristWeek = false;
		String week = "";
		int weekIn = 1;
		int preYear = 0;
		for(int i = minYear; i <= maxYear; i++) {
			if(preYear == 0) preYear = i;
			
			
			for (int j = minMonth; j <= maxMonth; j++) {
				Calendar cal = Calendar.getInstance();
				cal.set(i, j - 1, 1);
				int start = cal.get(Calendar.DAY_OF_WEEK);
				
				
				
				
//				System.out.printf("\t\t%d년 %d월\n", i, j);
//				System.out.printf("일\t월\t화\t수\t목\t금\t토\n");
//				for (int ll = 1; ll < start; ll++) { 
//				 	System.out.print("\t"); 
//				}
				
				
				
				for (int k = 1; k <= cal.getActualMaximum(Calendar.DATE); k++) {
					/* i == year
					 * j == month
					 * k == day
					 * start = day of week  1,2,3,4,5,6,7 -> 일,월,화,수,목,금,토
					 * start시작이 1보타 크면 주차를 안넣고 다시 1이될때가 1주차가됨.
					 * */
					System.out.printf("%d\t", k);
					String dbYear  = i+"";
					String dbMonth = j<10? "0"+j :j+"";
					String dbDay   = k<10? "0"+k :k+"";
					String workdate = dbYear+dbMonth+dbDay;
					CalPK pk = new CalPK(dbYear,dbMonth,dbDay);
					
					String dayOfWeek = "";
					String dayOfWeekEng = "";
					if(start == 1)      {dayOfWeek = "일"; dayOfWeekEng = "Sun";}
					else if(start == 2) {dayOfWeek = "월"; dayOfWeekEng = "Mon";}
					else if(start == 3) {dayOfWeek = "화"; dayOfWeekEng = "Tue";}
					else if(start == 4) {dayOfWeek = "수"; dayOfWeekEng = "Wed";}
					else if(start == 5) {dayOfWeek = "목"; dayOfWeekEng = "Thu";}
					else if(start == 6) {dayOfWeek = "금"; dayOfWeekEng = "Fri";}
					else if(start == 7) {dayOfWeek = "토"; dayOfWeekEng = "Sat";}
					
					
					
					if(!isFristWeek && start==1) {
						isFristWeek = true;
					}
					if(isFristWeek) {
						week = (weekIn<10?"0"+weekIn: weekIn)+"";
						if (start % 7 == 0) {
							
							weekIn++;
							if(preYear != i) {
								weekIn = 1;
								preYear = i;
							}
						}
					}
					if(start % 7 == 0) {
						start = 0;
					}
					start++;
					
					em.createNativeQuery("INSERT INTO "
										+ "ysy_calendar_mst (workdate , year , month, day , week"
										+ "                 ,DAY_OF_WEEK,DAY_OF_WEEK_eng,DAY_OF_WEEK_cnt"
										+ "                 ,reg_id , reg_dt ) "
										+ "VALUES (?,?,?,?,?,?,?,?,'admin'  , now())")
					.setParameter(1, workdate)
					.setParameter(2, dbYear)
					.setParameter(3, dbMonth)
					.setParameter(4, dbDay)
					.setParameter(5, week)
					.setParameter(6, dayOfWeek)
					.setParameter(7, dayOfWeekEng)
					.setParameter(8, k)
					.executeUpdate();
					
//					System.out.println("workdate ======>[" + workdate + "]week==" + week);
					
//					YsyCalendarMst calendar = YsyCalendarMst.builder()
//				            .calPK(pk)
//				            .dayOfWeekEng(dayOfWeekEng)
//				            .dayOfWeek(dayOfWeek)
//				            .dayOfWeekCnt(k)
//				            .week(week)
//				            .build();
					
//					if (start % 7 == 0) {
//						
//						System.out.println();
//					}
//					start++;
				}
//				System.out.println("");
			}
		}
		
		

	    

        
	   
        
		
        
//		List<YsyCalendarMst> calendarList = new ArrayList<>();
//        int lengthOfMonth = YearMonth.of(year, month).lengthOfMonth();
//		for(int day = 1; day <= lengthOfMonth; day++){
//			 CalPK pk = new CalPK(year+"",year+"",year+"");
//			 YsyCalendarMst calendar = YsyCalendarMst.builder()
//			            .calPK(pk)
//			            .build();
//		    
//		    calendarList.add(calendar);
//		}
		
//		em.createNativeQuery("INSERT INTO "
//				+ "ysy_calendar_mst (menu_id , btn_id , reg_dt, reg_id ) "
//			                + "VALUES (?       , ?      ,now()  , 'admin')")
//			.setParameter(1, menuId)
//			.setParameter(2, btn.getBtnId())
//			.executeUpdate();
		
		
		
		System.out.println("calendar create end =========================");
	}
	
	
}
