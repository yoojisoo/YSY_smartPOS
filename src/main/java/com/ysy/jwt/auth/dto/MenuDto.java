package com.ysy.jwt.auth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {
	
	private String menu_id;
	private String p_menu_id;
	private String menu_nm;
	private String menu_path;
	private String menu_full_path;
	private Integer menu_seq;
	private String is_admin;
	
	private List<Object[]> obj;
	
	
//	public MenuDto(Object[] obj) {
//		
//		for (int i = 0; i < obj.length; i++) {
//			if(obj[i] instanceof String)
//				menu_id = (String)obj[i];
//		}
//	}
	
//	public void setAllValues( String menu_id, String p_menu_id,String menu_nm,String menu_path,String menu_full_path,Integer menu_seq,String is_admin) {
//		 this.menu_id       = menu_id;       
//		 this.p_menu_id     = p_menu_id;     
//		 this.menu_nm       = menu_nm;       
//		 this.menu_path     = menu_path;     
//		 this.menu_full_path= menu_full_path;
//		 this.menu_seq      = menu_seq;      
//		 this.is_admin      = is_admin;      
//	}
//	
//	public MenuDto getObjec() {
//		
//		return this;
//	}
}
