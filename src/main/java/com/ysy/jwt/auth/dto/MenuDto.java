package com.ysy.jwt.auth.dto;

import com.ysy.jwt.auth.entity.YsyGrpMenuMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {
	
	private String p_menu_id;
	private String menu_id;
	private String menu_nm;
	private String menu_full_path;
	private String menu_path;
	private Integer menu_seq;
	private String is_admin;
	private String menu_icon;

	public MenuDto(YsyGrpMenuMap menu) {
		this.p_menu_id = menu.getYsyMenuMst().getPMenuId();
		this.menu_id = menu.getYsyMenuMst().getMenuId();
		this.menu_nm = menu.getMenuNm();
		this.menu_full_path = menu.getYsyMenuMst().getMenuFullPath();
		this.menu_path = menu.getYsyMenuMst().getMenuPath();
		this.menu_seq = menu.getYsyMenuMst().getMenuSeq();
		this.is_admin = menu.getYsyMenuMst().getIsAdmin();
		this.menu_icon = menu.getYsyMenuMst().getMenuIcon();
	}
}
