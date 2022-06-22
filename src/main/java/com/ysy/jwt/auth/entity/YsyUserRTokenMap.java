package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author clubbboy@naver.com
 * 2022 05 16
 * 토큰 갱신을 위한 user refresh token 저장 테이블.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
		name = "YSY_USER_RTOKEN_MAP"
//	    uniqueConstraints = {
//	        @UniqueConstraint(
//	            name = "YSY_USER_RTOKEN_MAP_contstraintName",
//	            columnNames = {"USER_ID", "REFRESH_TOKEN"}
//	        )
//	    }
	   ) 
@Entity
@Builder
@ToString
public class YsyUserRTokenMap extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
    @Column(name = "USER_ID")
    private String userId;

	
	@OneToOne
	@PrimaryKeyJoinColumn(name="USER_ID",referencedColumnName="USER_ID"  )
	private YsyUserMst ysyUserMst;
	
	
	@Column(name="REFRESH_TOKEN"  , columnDefinition = "TEXT" )
	private String refreshToken;

	public YsyUserRTokenMap(YsyUserMst userMst) {
        this.userId = userMst.getUsername();
        this.ysyUserMst = userMst;
    }
	
}
