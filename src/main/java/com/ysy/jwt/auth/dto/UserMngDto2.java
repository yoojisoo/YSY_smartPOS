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
public class UserMngDto2 {
	
	// userMst 정보
	private String user_id;
	private String user_nm;
	private String reg_dt;
	private String oauth_path;
	
	// grpMst 정보
	private String grp_id;
	private String biz_cd;
	
	// 주소 정보
	private List<YsyUserAddress> addrList = new ArrayList<YsyUserAddress>();

	
	public UserMngDto2(String user_id , List<YsyUserAddress> addrList) {
		this.user_id = user_id;
		this.addrList = addrList;
	}
	
	
	
	
}
