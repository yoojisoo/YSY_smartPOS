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
public class UserDto {
	
	private String user_id;
	private String user_phone;
	private String user_nm;
	private String reg_dt;
	private String grp_id;
	
	private List<Object[]> obj;
}
