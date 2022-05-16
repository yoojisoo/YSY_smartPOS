package com.ysy.jwt.auth.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author clubbbboy@naver.com
 * 2022 05 16
 * desc : 접근권한
 *        user와 group간의 매핑 테이블
 * **group과 role이 연결된다.
 * 
 */
//@Table(name="YSY_USER_GRP_MAP")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
		name = "YSY_USER_GRP_MAP",
	    uniqueConstraints = {
	        @UniqueConstraint(
	            name = "YSY_USER_GRP_MAP_contstraintName",
	            columnNames = {"USER_ID"}
	        )
	    }
	   )
//@Entity
public class YsyUserGrpMap extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	@JoinColumn(name ="USER_ID") 
	private YsyUserMst ysyUserMst;

	@OneToOne
	@JoinColumn(name ="GRP_ID")
	private YsyGrpMst ysyGrpMst;

	@OneToOne
	@JoinColumn(name ="BIZ_CD")
	private YsyBizMst ysyBizMst;
}
