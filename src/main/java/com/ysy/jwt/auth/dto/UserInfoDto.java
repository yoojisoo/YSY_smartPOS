package com.ysy.jwt.auth.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ysy.jwt.auth.entity.YsyUserAddress;
import com.ysy.jwt.auth.entity.YsyUserMst;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author clubbboy@naver.com
 * 
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder 
public class UserInfoDto {

	/* userMst 정보 */
	private String user_id;
	private String user_nm;
	private String oauth_path;
	
	/* grpMst 정보 */
	private String grp_id;
	private String biz_cd;
	
	/** 주소 정보  */
	private List<Addr> addrList = new ArrayList<Addr>();
	
	public UserInfoDto(List<YsyUserAddress> list) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		for(YsyUserAddress tmpAddr: list) {
			/* user정보 / group 정보 */
			if(user_id == null)    user_id    = tmpAddr.getYsyUserMst().getUsername();
			if(user_nm == null)    user_nm    = tmpAddr.getYsyUserMst().getName();
			if(oauth_path == null) oauth_path = tmpAddr.getYsyUserMst().getOAuthPath();
			if(grp_id  == null)    grp_id     = tmpAddr.getYsyUserMst().getYsyGrpMst().getGrpPK().getGrpId().toString();;
			if(biz_cd  == null)    biz_cd     = tmpAddr.getYsyUserMst().getYsyGrpMst().getGrpPK().getBizCd();
			
			/** 주소 배열 만듬 */
//			Addr addr = Addr.builder().addrCity(tmpAddr.getAddrCity()).build();
			Addr addr = new Addr();
			addr.addrType    = tmpAddr.getAddrType();                
			addr.addrZipCode = tmpAddr.getAddrZipCode();             
			addr.addrCity    = tmpAddr.getAddrCity();                
			addr.addrDetail  = tmpAddr.getAddrDetail();              
			addr.addrEtc     = tmpAddr.getAddrEtc();                 
			addr.phone1      = tmpAddr.getPhone1();                  
			addr.phone2      = tmpAddr.getPhone2();                  
			addr.reg_id      = tmpAddr.getRegId();                   
			addr.reg_dt      = tmpAddr.getRegDt().format(formatter); 
			addr.mod_id      = tmpAddr.getModId();                   
			addr.mod_dt      = tmpAddr .getModDt().format(formatter);
			addrList.add(addr);
		}
		
		
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
