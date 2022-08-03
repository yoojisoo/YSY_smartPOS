package com.ysy.jwt.auth.common;

public class SysEnum {
	public static enum enumGrps{
		DEFAULT_USER  ,                                //비회원
		ROLE_TEMP_USER,                                //준회원(회원가입 후 Default 등급)
		ROLE_USER     ,                                //정회원
		ROLE_USER_VIP1, ROLE_USER_VIP2, ROLE_USER_VIP3,//유료회원
		ROLE_MANAGER  , ROLE_MANAGER_SUPER,            //매니저
		ROLE_ADMIN                                     //관리자
	}
	public static enum enumBtns{
		SEARCH , SAVE , MODIFY , DELETE , DOWNLOAD , UPLOAD
	}
}
