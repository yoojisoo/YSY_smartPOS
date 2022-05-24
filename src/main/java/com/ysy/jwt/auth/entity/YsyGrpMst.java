package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ysy.common.SysEnum.enumGrps;
import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author clubbboy@naver.com
 * 2022 05 17
 * desc : 권한 그룹 테이블.
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="YSY_GRP_MST")
@Entity
public class YsyGrpMst extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private GrpPK grpPK;
	
	@MapsId("bizCd")
    @ManyToOne
    @JoinColumn(name = "BIZ_CD" , referencedColumnName = "BIZ_CD")
    private YsyBizMst ysyBizMst;
	
	@Column(name = "GRP_NM" , length= 50 ) 
	private String grpNm;
	
	@ColumnDefault("'Y'")
	@Column(name = "USE_YN" , length = 1  )
	private String useYn;
	
	//낮은 level일수록 높은 권한 획득
	@Column(name="LEVEL_ID" , length = 20 , nullable = false)
	private int levelId;//desc 보면됨/  0: admin , 200 : manager , 300 : user 
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="BIZ_CD",referencedColumnName="BIZ_CD" , nullable = false )
//	private YsyBizMst ysyBiz; 
	
	//mappedBy -> 나와 관련된 클래스를 조회만 할 수 있게 해줌
	@OneToMany(mappedBy = "ysyGrpMst" , fetch = FetchType.LAZY)
	@JsonIgnoreProperties//해당 어노테이션은 무한반복을 안되게 함.
	private List<YsyGrpMenuMap> ysyGrpMenuMap = new ArrayList<YsyGrpMenuMap>(); 
	
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable //pk 여러개 생성시 사용 : @EmbeddedId 
	public static class  GrpPK implements Serializable{
		
		private static final long serialVersionUID = 1L;

		@Column(name = "GRP_ID" , length = 50 , nullable = false )
		@Enumerated(EnumType.STRING)
		private enumGrps grpId;
		
		private String bizCd;
	}
	
}


