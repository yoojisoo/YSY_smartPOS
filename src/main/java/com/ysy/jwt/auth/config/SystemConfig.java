package com.ysy.jwt.auth.config;

public class SystemConfig {
	
	public static final String SEVER_URL = "http://localhost:8000"; 
//	public static final String SEVER_URL = "http://tboom.shop";
//	public static final String IMAGE_URL = "https://ysy899.cdn1.cafe24.com";
	public static final String IMAGE_URL = "/ysy899";
	
	public static final String KAKAO_RE_URL = SEVER_URL+"/kakaoLogin/getCode";
	public static final String NAVER_RE_URL = SEVER_URL+"/naverLogin/getCode";
	
	
	public static final String FROM_ADDRESS = "mnew2m@gmail.com";

}
