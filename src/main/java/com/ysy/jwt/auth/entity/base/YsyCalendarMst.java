package com.ysy.jwt.auth.entity.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author clubbboy@naver.com
 * 
 * calendar mst table
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name ="YSY_CALENDAR_MST")
@Entity
public class YsyCalendarMst extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	CalPK calPK;
	
	@Column(name = "WORKDATE" , length= 8 )
	private String workDate;
	@Column(name = "WEEK" , length= 6 )
	private String week;
	@Column(name = "DAY_OF_WEEK" , length= 3 )
	private String dayOfWeek; 
	@Column(name = "DAY_OF_WEEK_ENG" , length= 3 )
	private String dayOfWeekEng; 
	@Column(name = "DAY_OF_WEEK_CNT"  )
	private int dayOfWeekCnt; 
	
	
	@Embeddable
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CalPK implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		
		@Column(name = "YEAR" , length= 4 ) 
		private String year;
		@Column(name = "MONTH" , length= 2 )
		private String month;
		@Column(name = "DAY" , length= 2 )
		private String day;
		
	}

}
