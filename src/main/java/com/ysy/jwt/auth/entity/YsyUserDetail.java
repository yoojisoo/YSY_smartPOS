package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
//@Builder
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "YSY_USER_DTL")
//@Entity
public class YsyUserDetail extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
//	@EmbeddedId
//	private PK pk;
//	
//	@Column(name="S_TIME" )
//	protected LocalDateTime startTime;
//	@Column(name="E_TIME" )
//	protected LocalDateTime endTime;
//	@Column(name="USER_GRADE" , length = 50)
//	private String grade;
//	@Column(name="USER_POINT" )
//	private double point;
//	@Column(name="USER_COUPON" , length = 250)
//	private String coupon;//,,,,,
//	
////	@MapsId("userId")
////	@OneToMany(fetch = FetchType.LAZY)
////	@JoinColumns({@JoinColumn(name="USER_ID" , referencedColumnName="USER_ID", updatable = false),
////                  @JoinColumn(name="GRP_ID" , referencedColumnName="GRP_ID" , updatable = false),
////                  @JoinColumn(name="BIZ_CD" , referencedColumnName="BIZ_CD" , updatable = false)})
////	private List<YsyUserMst> ysyUserMst;
//	@MapsId("userId")
//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name="USER_ID" , referencedColumnName="USER_ID", updatable = false)
//	private List<YsyUserMst> ysyUserMst1;
//	
//	@MapsId("grpId")
//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name="GRP_ID" , referencedColumnName="GRP_ID", updatable = false)
//	private List<YsyUserMst> ysyUserMst2;
//	
//	@MapsId("bizCd")
//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name="BIZ_CD" , referencedColumnName="BIZ_CD", updatable = false)
//	private List<YsyUserMst> ysyUserMst3;
//	
//	@Embeddable
//	public static class PK implements Serializable{
//		
//		
//
//		private String userId;
//		private String grpId;
//		private String bizCd;
//		
//		@Column(name = "USER_DTL_ID")
//		private String userDetailId;
//	}
}
