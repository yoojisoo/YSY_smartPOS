package com.ysy.jwt.auth.entity;

import java.io.Serializable;

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
import javax.persistence.Table;

import com.ysy.common.SysEnum.enumBtns;
import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_BTN_MST")
@Entity
public class YsyBtnMst extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private BtnPK btnPK;
	
	@MapsId("menuId") 
	@JoinColumn(name = "MENU_ID" , referencedColumnName = "MENU_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private YsyMenuMst ysyMenuMst;  
	
	@Column(name = "USE_YN" , nullable = false)
	private String useYn;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable
	public static class BtnPK implements Serializable{
		private static final long serialVersionUID = 1L;

		@Column(name = "MENU_BTN" , length = 50)
		@Enumerated(EnumType.STRING)
		private enumBtns menuBtn; 
		 
		private String menuId;
		//a - s
		// a - m
		// a - save
	}
}
