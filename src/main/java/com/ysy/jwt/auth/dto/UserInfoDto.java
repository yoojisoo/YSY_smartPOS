package com.ysy.jwt.auth.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ysy.jwt.auth.entity.YsyUserAddress;

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
	
	public UserInfoDto(String user_id , String user_nm , String oauth_path , String grp_id , String biz_cd
			          ,String addrType , String addrZipCode , String addrCity , String addrDetail , String addrEtc
			          ,String phone1,String phone2,String reg_id,LocalDateTime reg_dt,String mod_id,LocalDateTime mod_dt) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		/* user정보 / group 정보 */
		this.user_id    = user_id;
		this.user_nm    = user_nm   ;
		this.oauth_path = oauth_path;
		this.grp_id     = grp_id    ;
		this.biz_cd     = biz_cd    ;
		Addr addr = new Addr();
		addr.addrType    = addrType    ;           
		addr.addrZipCode = addrZipCode ;           
		addr.addrCity    = addrCity    ;           
		addr.addrDetail  = addrDetail  ;           
		addr.addrEtc     = addrEtc     ;           
		addr.phone1      = phone1      ;           
		addr.phone2      = phone2      ;           
		addr.reg_id      = reg_id      ;           
		addr.reg_dt      = reg_dt.format(formatter); 
		addr.mod_id      = mod_id      ;                   
		addr.mod_dt      = mod_dt.format(formatter); 
		addrList.add(addr);
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
