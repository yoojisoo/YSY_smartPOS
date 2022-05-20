package com.ysy.jwt.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {
	
	private String bizCd;
	private String username;
	private String password;
	private String name;
	private String email;
}
