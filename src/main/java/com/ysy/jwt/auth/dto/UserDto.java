package com.ysy.jwt.auth.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ysy.jwt.auth.common.SysEnum.enumGrps;
import com.ysy.jwt.auth.entity.YsyUserAddress;
import com.ysy.jwt.auth.entity.YsyUserMst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
 * @author s_plus7
 * 22.07.27
 * Desc : 유저 관리 페이지 DTO
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	// userMst 정보
	private String userId;
	private String userNm;
	private String regDt;
	private String oauthPath;
	private String modDt;
	private String password;
	private String useYn;
	private String isEmailAuth;
	
	// bizMst 정보
	private String bizCd;
	private String bizNm;
	
	// grpMst 정보
	private enumGrps grpId; //권한 아이디
	private String grpNm; //권한 이름
	
	/** 주소정보 */
	private String addrType;
	private String addrZipCode;
	private String addrCity;
	private String addrDetail;
	private String addrEtc;
	private String phone1;
	private String phone2;
	private String addrRegId;
	private String addrRegDt;
	private String addrModId; 
	private String addrModDt;
	
	/* 유저 1명의 userMst 테이블만 조회 */
	public UserDto(YsyUserMst user) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.userId = user.getUsername();
		this.userNm = user.getName();
		this.modDt = user.getModDt().format(formatter);
		this.regDt = user.getRegDt().format(formatter);
		this.oauthPath = user.getOAuthPath();
		this.password = user.getPassword();
		this.useYn = user.getUseYn();
		this.isEmailAuth = user.getIsEmailAuth();
		
		this.bizNm = user.getYsyGrpMst().getYsyBizMst().getBizNm();
		this.bizCd = user.getYsyGrpMst().getGrpPK().getBizCd();
		
		this.grpId = user.getYsyGrpMst().getGrpPK().getGrpId();
		this.grpNm = user.getYsyGrpMst().getGrpNm();
	}
	
	/* 유저 1명의 어드레스 조회 */
	public UserDto(YsyUserAddress addr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.addrType 	 = addr.getAddrType();
		this.addrZipCode = addr.getAddrZipCode();
		this.addrCity 	 = addr.getAddrCity();
		this.addrDetail  = addr.getAddrDetail();
		this.addrEtc 	 = addr.getAddrEtc();
		this.phone1 	 = addr.getPhone1();
		this.phone2 	 = addr.getPhone2();
		this.addrRegId   = addr.getRegId();
		this.addrRegDt   = addr.getRegDt().format(formatter);
		this.addrModId   = addr.getModId(); 
		this.addrModDt   = addr.getModDt().format(formatter);
	}
	
	public UserDto(String grpNm) {
		this.grpNm = grpNm;
	}
	
	
	
}
