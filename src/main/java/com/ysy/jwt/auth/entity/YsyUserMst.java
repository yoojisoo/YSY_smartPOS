package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

	

	@OneToMany(mappedBy = "ysyUserMst")//, fetch = FetchType.LAZY
//	@JsonBackReference //순환참조 방지
//	@JsonIgnoreProperties({"ysyUserMst"})
//	@JsonManagedReference
	@JsonIgnore
//	private List<YsyUserAddress> addressList = new ArrayList<YsyUserAddress>();
	private Set<YsyUserAddress> addressList = Collections.<YsyUserAddress>emptySet();  //new HashSet<YsyUserAddress>();
	
	//이메일 인증 여부
	@Column(name = "IS_EMAIL_AUTH" , length = 1)
	private String isEmailAuth;

	// ROLE 정보 들어있는 object
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "GRP_ID", referencedColumnName = "GRP_ID", nullable = false),
			       @JoinColumn(name = "BIZ_CD", referencedColumnName = "BIZ_CD", nullable = false) })
//	@JsonIgnore
	private YsyGrpMst ysyGrpMst;
	
	//가입경로 : 홈페이지일 경우 빈값임. 현재는 kakao /naver 만 구현됨
	@Column(name = "OAuth_PATH" , length = 50)
	@Comment(value="kakao 또는 naver / google 등의 간편 로그인 사용자 가입경로")
	private String oAuthPath;

	public List<String> getRoleList() { 
		if (this.ysyGrpMst.getGrpPK().getGrpId() != null)
			return Arrays.asList(ysyGrpMst.getGrpPK().getGrpId().toString().split(","));
		else
			return new ArrayList<String>();
	}

	
}
