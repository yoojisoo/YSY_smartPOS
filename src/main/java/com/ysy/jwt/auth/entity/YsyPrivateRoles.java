package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.ysy.jwt.auth.entity.YsyBtnMst.BtnPK;
import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author clubbboy@nave.com
 * 2022 05 17
 * desc : 그룹권한과는 별개로 권한을 주고싶을때 사용하는 테이블.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_PRIVATE_ROLES")
@Entity
public class YsyPrivateRoles extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private PK pk;
	
	@MapsId("menuId")
	@JoinColumn(name = "MENU_ID" , referencedColumnName = "MENU_ID")
	@ManyToOne
	private YsyMenuMst ysyMenuMst; 

	@MapsId("userId")
	@JoinColumn(name = "USER_ID" , referencedColumnName = "USER_ID")
	@ManyToOne
	private YsyUserMst ysyUserMst; 
	
	@Column(name="BTNS" , length = 255 , nullable = false)
	private String btns;//search , mod
	
	@Data
	@Embeddable
	public class PK implements Serializable {
		private static final long serialVersionUID = 1L;
		private String userId;
		private String menuId;
	}
}
