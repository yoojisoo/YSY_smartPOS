package com.ysy.jwt.auth.dto;

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
	private int    menu_seq;
	private String is_admin;
	
}
