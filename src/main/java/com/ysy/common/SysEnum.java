package com.ysy.common;

public class SysEnum {

	
	//ROLE_TEMP_USER 준회원 (회원가입시 기본 ) .관리자가 권한 부여해줘야함.
	public static enum enumGrps{
		DEFAULT_USER , 
		ROLE_TEMP_USER , ROLE_USER , ROLE_USER_VIP1 , ROLE_USER_VIP2 , ROLE_USER_VIP3 ,  
		ROLE_MANAGER_SUPER , ROLE_MANAGER ,    
		ROLE_ADMIN
	}
	public static enum enumBtns{
		SEARCH , SAVE , MODIFY , DELETE , DOWNLOAD , UPLOAD
	}
	
	
}
