package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author clubbboy@naver.com
 *  2022 05 16
 *  desc : 메뉴 테이블.  
 */

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_MENU_MST")
@Entity
public class YsyMenuMst extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private MenuPK menuPK;
	
//	@MapsId("bizCd")
//	@ManyToOne
//	@JoinColumn(name = "BIZ_CD" , referencedColumnName = "BIZ_CD")
//	private YsyBizMst ysyBizMst;
	//a 1
	// a-1 1 a-2 2
	// b 2
	@Id
	@Column(name = "MENU_ID" , length = 50)
//	@JsonIgnore
	private String menuId;
	
	@Column(name="P_MENU_ID"  , length=50 )
	private String pMenuId;
	@Column(name="MENU_PATH"  , length=250 , nullable = false)
	private String menuPath;
	@Column(name="MENU_NM"    , length=50  , nullable = false)
	private String menuNm;
	@Column(name="MENU_SEQ"   , length=10  , nullable = false)
	private int menuSeq; 
	@ColumnDefault("'Y'")
	@Column(name="USE_YN"     , length=1   , nullable = false)
	private String useYn;

	@Column(name="MENU_FULL_PATH"     , length=250   , nullable = false)
	private String menuFullPath;
	
	@Column(name="IS_ADMIN"   , length=1   , nullable = false)//admin 메뉴 여부
	private String isAdmin;
	
	
//	@BatchSize(size = 5) // Batch size를 지정한다
//	@OneToMany(mappedBy = "ysyMenuMst" , fetch = FetchType.EAGER)
////	@JsonIgnoreProperties
//	@JsonIgnore
//	private List<YsyBtnMst> ysyBtnMst = new ArrayList<YsyBtnMst>();
	
//	@Data
//	@NoArgsConstructor
//	@AllArgsConstructor
//	@Embeddable //pk 여러개 생성시 사용 : @EmbeddedId
//	public static class MenuPK implements Serializable{ 
//		
//		private static final long serialVersionUID = 1L;
//
//		@Column(name = "MENU_ID" , length = 50)
//		private String menuId;
//		
////		private String bizCd;
//	}
	
}
