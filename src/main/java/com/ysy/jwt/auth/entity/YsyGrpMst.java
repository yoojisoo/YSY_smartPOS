package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="YSY_GRP_MST")
@Entity
public class YsyGrpMst extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

//	@Id
//	private String sss;
	
	@EmbeddedId
	private GrpPK pk;
	
	@MapsId("bizCd")
    @ManyToOne
    @JoinColumn(name = "BIZ_CD" , referencedColumnName = "BIZ_CD")
    private YsyBizMst ysyBizMst;
	
//	@Enumerated
//	@Column(name = "GRP_ID" , length = 50 , nullable = false )
//	private String grpId;
	@Column(name = "GRP_NM" , length= 50 )
	private String grpNm;
	@Column(name = "USE_YN" , length = 1  )
	private String useYn;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="BIZ_CD",referencedColumnName="BIZ_CD" , nullable = false )
//	private YsyBizMst ysyBiz; 
	
	
	
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable //pk 여러개 생성시 사용 : @EmbeddedId
	public class GrpPK implements Serializable{
		
		private static final long serialVersionUID = 1L;

		@Column(name = "GRP_ID" , length = 50 , nullable = false )
		private String grpId;
		
		private String bizCd;
	}
	
}


