package com.ysy.jwt.auth.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.Tuple;
import com.ysy.jwt.auth.entity.QYsyUserAddress;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.YsyUserAddress;
import com.ysy.jwt.auth.entity.YsyUserMst;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
 * 22 07 07 yoojisoo
 */
@Data
@NoArgsConstructor
public class UserMngDto {
	
	// userMst 정보
	private String userId;
	private String userNm;
	private String regDt;
	private String oauthPath;
	
	// grpMst 정보
//	private String grpId;
//	private String bizCd;
	private String bizNm;
	
	
	/** 주소정보 */
	List<YsyUserAddress> addrList = new ArrayList<YsyUserAddress>();
//	private String addrType;
//	private String addrZipCode;
//	private String addrCity;
//	private String addrDetail;
//	private String addrEtc;
//	private String phone1;
//	private String phone2;
//	private String addr_reg_id;
//	private String addr_reg_dt;
//	private String addr_mod_id; 
//	private String addr_mod_dt;
	
	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	/* 유저 1명의 userMst 테이블만 조회 */
	public UserMngDto(YsyUserMst user) {
		if(this.userId == null)		this.userId 	= user.getUsername();
		if(this.userNm == null)		this.userNm 	= user.getName();
		if(this.regDt == null)		this.regDt 		= user.getRegDt().format(formatter);
		if(this.oauthPath == null)	this.oauthPath 	= user.getOAuthPath();
	}
	
	/* 유저 1명의 userMst 테이블 + 어드레스 조회 */
	public UserMngDto(String username, String name, LocalDateTime regDt, String oAuthPath, String bizNm,
			List<YsyUserAddress> addressList) {
		this.userId = username;
		this.userNm = name;
		this.regDt = regDt.format(formatter);
		this.oauthPath = oAuthPath;
		this.bizNm = bizNm;
		this.addrList = addressList;
	}
	
	/* 유저 1명의 userMst 테이블 + 어드레스 조회 */
//	public UserMngDto(YsyUserMst user, List<YsyUserAddress> addrList) {
//		this.userId = user.getUsername();
//		this.userNm = user.getName();
//		this.regDt = user.getRegDt().format(formatter);
//		this.oauthPath = user.getOAuthPath();
//		this.bizNm = user.getYsyGrpMst().getYsyBizMst().getBizNm();
//		this.addrList = addrList;
//
//	}
	


	
	
	
}
