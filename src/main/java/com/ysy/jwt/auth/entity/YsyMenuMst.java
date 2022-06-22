package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.ysy.jwt.auth.dto.MenuDto;
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
//@SqlResultSetMapping(
//        name="menuInfoMapping",
//        classes = @ConstructorResult(
//                targetClass = MenuDto.class,
//                columns = {
//                        @ColumnResult(name="menu_id"        , type = String.class),
//                        @ColumnResult(name="p_menu_id"      , type = String.class),
//                        @ColumnResult(name="menu_nm"        , type = String.class),
//                        @ColumnResult(name="menu_path"      , type = String.class),
//                        @ColumnResult(name="menu_full_path" , type = String.class),
//                        @ColumnResult(name="menu_seq"       , type = Integer.class),
//                        @ColumnResult(name="is_admin"       , type = String.class),
//                })
//)
@Entity
public class YsyMenuMst extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "MENU_ID" , length = 50)
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
	
	@ColumnDefault("mdi-home")
	@Column(name="MENU_ICON"  , length=50  , nullable=false)
	private String menuIcon;
}
