package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "YSY_MENU_BTN_MAP")
@Entity
public class YsyMenuBtnMap extends BaseEntity implements Serializable{

	
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private BtnMenuPK mapPK;
	
	@MapsId("menuId") 
	@ManyToOne
	@JoinColumn(name = "MENU_ID" , referencedColumnName = "MENU_ID")
	private YsyMenuMst ysyMenuMst;

	@MapsId("btnId") 
	@ManyToOne
	@JoinColumn(name = "BTN_ID" , referencedColumnName = "BTN_ID")
	private YsyBtnMst ysyBtnMst;
	

	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable //pk 여러개 생성시 사용 : @EmbeddedId 
	public static class BtnMenuPK implements Serializable{
		
		private static final long serialVersionUID = 1L;
		private String menuId;
		private String btnId;
	}
}
