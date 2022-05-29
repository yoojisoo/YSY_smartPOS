package com.ysy.jwt.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {
	
	private String bizCd;
	private String username;
	private String password;
	private String name;
	private String email;
	private String oAuthPath;//가입경로 : 빈값이면 홈페이지 , kakao ...
}
