package com.ysy.jwt.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author clubbboy@naver.com
 * desc : sns(kakao , naver 등등) 최초 로그인시 DB에 저장하기 위한 data class
 *
 */
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
