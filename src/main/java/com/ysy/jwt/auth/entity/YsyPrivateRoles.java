package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

/**
 * @author clubbboy@nave.com
 * 2022 05 17
 * desc : 그룹권한과는 별개로 권한을 주고싶을때 사용하는 테이블.
 *  * 최우선 되는 권한 테이블 
 *    -> 그룹쪽에 권한이 있고 여기에도 권한이 있으면 여기에 있는 권한이 우선시 된다.
 *    -> 여기에 권한이 없을시 그룹쪽에 권한을 따라감.
 *    ex1) clubbboy@naver.com에 대한 권한 : 
 *         menu001에 대한 권한이 grpMenuMap에 있고 버튼에 대한 권한이 search,save 있는상태에서
 *         해당 테이블에 menu001에 대한 권한이 search가 있으면 최종적으로 search권한만 가지고 간다.
 *    ex2) 해당 테이블에만 menu001에 대한 권한이 있으면 grpMenuMap에 권한이 없더라도 menu001에 대한 권한을 가져감.    
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
	
	@Data
	@Embeddable
	public static class PK implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String userId;
		private String menuId;
		
		@Column(name="BTN" , length = 50)
		@Enumerated(EnumType.STRING)
		private enumBtns btn;
	}
}
