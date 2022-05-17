package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_CODE_MST")
@Entity
public class YsyCodeMst extends BaseEntity implements Serializable{

	@EmbeddedId
	private CodePK codePK;

	
	@MapsId("bizCd")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="BIZ_CD" , referencedColumnName = "BIZ_CD" )
	private YsyBizMst ysyBizMst;
	
	@Column(name="DTL_NM") 
	private String dtlNm;
	@Column(name="DEL_YN")
	private String delYn;

	
	@Embeddable
	public static class CodePK implements Serializable{
		
		private String bizCd;
		@Column(name="MST_CD")
		private String mstCd;
		@Column(name="DTL_CD")
		private String dtlCd;
	}
	
	
}
