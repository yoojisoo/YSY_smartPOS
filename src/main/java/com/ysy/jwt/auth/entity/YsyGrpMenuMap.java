package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ysy.jwt.auth.entity.YsyGrpMst.GrpPK;
import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/***
 * @author clubbboy@naver.com
 *  2022 05 17
 *  권한그룹과 메뉴 매핑
 *	
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_GRP_MENU_MAP")
@Entity
public class YsyGrpMenuMap extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private GrpMenuPK mapPK;
	
//	@MapsId("bizCd")
//	@ManyToOne
//	@JoinColumn(name = "BIZ_CD" , referencedColumnName = "BIZ_CD")
//	private YsyBizMst ysyBizMst;
//	
//	@MapsId("grpId")
//	@JoinColumn(name = "GRP_ID" , referencedColumnName = "GRP_ID")
//	private YsyGrpMst ysyGrpMst;
//
	@MapsId("menuId") 
	@ManyToOne
	@JoinColumn(name = "MENU_ID" , referencedColumnName = "MENU_ID")
	private YsyMenuMst ysyMenuMst;

	@MapsId("grgPK")
	@ManyToOne
	@JoinColumns({@JoinColumn(name = "BIZ_CD" , referencedColumnName = "BIZ_CD"),
		          @JoinColumn(name = "GRP_ID" , referencedColumnName = "GRP_ID")})
	private YsyGrpMst ysyGrpMst ; 
//	
//	@MapsId("menuPK")
//	@ManyToOne
////	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumns({@JoinColumn(name = "BIZ_CD"  , referencedColumnName = "BIZ_CD"),
//		          @JoinColumn(name = "MENU_ID" , referencedColumnName = "MENU_ID")})
//	private YsyMenuMst ysyMenuMst ;
	
//	@MapsId("menuId")
//	@JoinColumn(name = "MENU_ID" , referencedColumnName = "MENU_ID")
//	private YsyMenuMst ysyMenuMst ;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable //pk 여러개 생성시 사용 : @EmbeddedId 
	public class GrpMenuPK implements Serializable{
		
		private static final long serialVersionUID = 1L;
//		private String bizCd;
//		private String grpId;  
//		@Column(name = "MENU_ID")
//		private String menuId;
		private String menuId;
		private GrpPK grgPK;
	}
}
