package com.ysy.jwt.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;

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
@Table(
		name = "YSY_MENU_MST",
	    uniqueConstraints = {
	        @UniqueConstraint(
	            name = "YSY_MENU_MST_contstraintName",
	            columnNames = {"BIZ_CD", "MENU_ID"}
	        )
	    }
	   )
//@Entity
public class YsyMenuMst extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "BIZ_CD" , referencedColumnName = "BIZ_CD")
	private YsyBizMst ysyBizMst; 
	
	@Column(name="P_MENU_ID"  , length=50 )
	private String pMenuId;
	@Column(name="MENU_ID"    , length=50  , nullable = false)
	private String menuId;
	@Column(name="MENU_PATH"  , length=250 , nullable = false)
	private String menuPath;
	@Column(name="MENU_NM"    , length=50  , nullable = false)
	private String menuNm;
	@Column(name="MENU_SEQ"   , length=10  , nullable = false)
	private int menuSeq;
	@ColumnDefault("N")
	@Column(name="USE_YN"     , length=1   , nullable = false)
	private String useYn;
	
}
