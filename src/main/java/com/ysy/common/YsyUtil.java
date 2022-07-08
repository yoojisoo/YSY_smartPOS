package com.ysy.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

@Component
public class YsyUtil {
	
	public String PJT_NAME = "S-POS";
	
	
	private  boolean isLog = true;
	
	public  void log(Object obj) {
		if(isLog) System.out.println(obj.getClass().getName());
	}
	public  void log(String msg) {
		if(isLog) System.out.println(getTime() + " : " + msg);
	}
	
	public  String getTime() {
		
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		return date + " " + time;
	}

	//널 ,빈값 체크
	public  boolean isNullAndEmpty(String str) {
		boolean flag = false;
		
		if(str == null)               flag = true;
		else if(str.trim().isEmpty()) flag = true;
		return flag;
	}
	public  boolean isNull(String str) {
		if(str== null) return true;
		return false;
	}
	
	/** sort 객체 생성 asc기준 */
	public Sort getSort(String ...args){
		if(args == null || args.length == 0) return null;
		
		List<Order> sortList = new LinkedList<>();  
		for (int i = 0; i < args.length; i++) {
			sortList.add(Order.asc(args[i]));  
		}
		Sort sort = Sort.by(sortList);  
		
		return sort;
	}
	
	/** DB key 생성 (오늘날짜) */
	public String createDbKey(String keyNm ) {
		LocalDateTime dateAndtime = LocalDateTime.now();
//		 LocalDate now = LocalDate.now();
//		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd HH_mm_ss");         // 포맷 적용        
//		 String formatedNow = now.format(formatter);         // 결과 출력        
		  
		return keyNm + "_"+dateAndtime;
		
	}
	
	/** DB KEY 생성 (UUID) */
	public String createUUID(String keyNm) {
		UUID uuid = UUID.randomUUID();
		
		return keyNm + "-" + uuid.toString();
	}
}
