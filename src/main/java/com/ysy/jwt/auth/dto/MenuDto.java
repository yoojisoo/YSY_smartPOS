package com.ysy.jwt.auth.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MenuDto {

	private String menuId;
	private String menuName;
	private String menuPath;
	private String isAdmin;
	private List<MenuDto> childMenuDto = new ArrayList<MenuDto>();
	
	
}
