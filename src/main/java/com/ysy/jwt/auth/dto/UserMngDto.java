package com.ysy.jwt.auth.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ysy.jwt.auth.dto.UserInfoDto.Addr;
import com.ysy.jwt.auth.dto.UserInfoDto.Addr.AddrBuilder;
import com.ysy.jwt.auth.entity.YsyUserAddress;
import com.ysy.jwt.auth.entity.YsyUserMst;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMngDto {
	
	// userMst 정보
	private String user_id;
	private String user_nm;
	private String reg_dt;
	private String oauth_path;
	
	// grpMst 정보
	private String grp_id;
	private String biz_cd;
	
	// 주소 정보
	private List<Addr> addrList = new ArrayList<Addr>();
	
	
	/* 유저 1명 , userMst 테이블 조회 */
	public UserMngDto(YsyUserMst user) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		if(this.user_id == null)	this.user_id = user.getUsername();
		if(this.user_nm == null)	this.user_nm = user.getName();
		if(this.reg_dt == null)		this.reg_dt = user.getRegDt().format(formatter);
		if(this.oauth_path == null)	this.oauth_path = user.getOAuthPath();
		
//		if(this.grp_id == null)		this.grp_id = user.getYsyGrpMst().getGrpPK().getGrpId().toString();
//		if(this.biz_cd == null)		this.biz_cd	= user.getYsyGrpMst().getGrpPK().getBizCd();
	}
	
	/* 유저 1명 , 연관 테이블 조회 */
//	public UserDto(YsyUserMst user) {
//		
//	}
	
	/* 모든 유저 정보 조회 */
	public UserMngDto(List<YsyUserMst> userList) {
		
	}
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Addr{
		private String addrType;
		private String addrZipCode;
		private String addrCity;
		private String addrDetail;
		private String addrEtc;
		private String phone1;
		private String phone2;
		private String reg_id;
		private String reg_dt;
		private String mod_id; 
		private String mod_dt;
	}
	
	
}
