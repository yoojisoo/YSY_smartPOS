package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * @Table(
		name = "YSY_USER_GRP_MAP",
	    uniqueConstraints = {
	        @UniqueConstraint(
	            name = "YSY_USER_GRP_MAP_contstraintName",
	            columnNames = {"USER_ID"}
	        )
	    }
	   )
 * */

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_USER_MST")
@Entity
public class YsyUserMst extends BaseEntity implements Serializable {

	// @GeneratedValue(strategy = GenerationType.AUTO)
	// private long id;
	private static final long serialVersionUID = 1L;

	@Id
	// String username으로 한 이유는 스프링 정책상 변수를 맞춰주기 위해 , 다른걸로 하려면 셋팅을 좀 해야해서 그냥 db컬럼명을
	// 지정해주고 처리함. 패스워드도 동일한 이유임.
	// user id = email임.
	@Column(name = "USER_ID", length = 255, nullable = false)
	private String username;
	@Column(name = "USER_PW", length = 255, nullable = false)
	private String password;
	@Column(name = "USER_NM", length = 255, nullable = false)
	private String name;
	@Column(name = "USER_ADDR" , length = 255)
	private String addr;
	

	@Embedded
	private Address address;
	@Column(name = "IS_EMAIL_AUTH" , length = 1)
	private String isEmailAuth;

	// ROLE 정보 들어있는 object
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "GRP_ID", referencedColumnName = "GRP_ID"),
			       @JoinColumn(name = "BIZ_CD", referencedColumnName = "BIZ_CD") })
	private YsyGrpMst ysyGrpMst;
	
	//가입경로 : 홈페이지일 경우 빈값임. 현재는 kakao 만 구현됨
	@Column(name = "OAuth_PATH" , length = 50)
	@Comment(value="kakao 또는 naver / google 등의 간편 로그인 사용자 가입경로")
	private String oAuthPath;

	public List<String> getRoleList() { 
		if (this.ysyGrpMst.getGrpPK().getGrpId() != null)
			return Arrays.asList(ysyGrpMst.getGrpPK().getGrpId().toString().split(","));
		else
			return new ArrayList<String>();
	}

	@Embeddable
	public class Address {
		@Column(name = "ADDR_ZIP_CODE")
		private String addrZipCode;
		@Column(name = "ADDR_CITY")
		private String addrCity;
		@Column(name = "ADDR_DETAIL")
		private String addrDetail;
		@Column(name = "ADDR_ETC")
		private String addrEtc;
		@Column(name = "USER_PHONE")
		private String phone;
	}
}
