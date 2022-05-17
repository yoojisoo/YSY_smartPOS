package com.ysy.jwt.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinModel {
	
	private String bizCd;
	private String username;
	private String password;
	private String name;
}
