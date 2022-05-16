package com.ysy.jwt.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author clubbboy@naver.com
 * 2022 05 16
 * 토큰 갱신을 위한 user refresh token 저장 테이블.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
		name = "YSY_USER_RTOKEN_MAP",
	    uniqueConstraints = {
	        @UniqueConstraint(
	            name = "YSY_USER_RTOKEN_MAP_contstraintName",
	            columnNames = {"USER_ID", "BIZ_CD","REFRESH_TOKEN"}
	        )
	    }
	   ) 
//@Entity
public class YsyUserRTokenMap extends BaseEntity{

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private long id;
	
	@OneToOne
	@JoinColumn(name="BIZ_CD",referencedColumnName="BIZ_CD" , nullable = false  )
	private YsyBizMst ysyBizMst;
	
	@OneToOne
	@JoinColumn(name="USER_ID",referencedColumnName="USER_ID" , nullable = false )
	private YsyUserMst ysyUserMst;
	
	@Column(name="REFRESH_TOKEN" , nullable = false )
	private String token;

	
}
