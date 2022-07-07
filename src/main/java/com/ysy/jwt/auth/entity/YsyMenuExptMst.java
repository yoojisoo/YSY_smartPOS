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

import org.hibernate.annotations.Comment;

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
 * desc : user에 대한 role을 올렸을때 보여주지 말하야 하는 메뉴를 처리하는 테이블.
 *       ex) user -> manager로 권한 변경시 manager가 접근할 수 있는 메뉴를 default로 모두 보여주지 못하게 하고
 *           해당 메니저가 풀어준 메뉴만 볼수 있는 권한을 가짐.
 *           IS_VIEW가 Y인 메뉴만 볼 수 있음.
 *           expt menu관리 페이지에서 변경 가능함.  
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_MENU_EXPT_MST") 
@Entity
public class YsyMenuExptMst extends BaseEntity implements Serializable {

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
	
	@Comment("메뉴를 보여줄지 여부")
	@Column(name="IS_VIEW" , length = 1 , nullable = false)
	private String isView;
	
	
	
	@Data
	@Embeddable
	public static class PK implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String userId;
		private String menuId;
		
//		@Column(name="BTN" , length = 50)
//		@Enumerated(EnumType.STRING)
//		private enumBtns btn;
	}
}
