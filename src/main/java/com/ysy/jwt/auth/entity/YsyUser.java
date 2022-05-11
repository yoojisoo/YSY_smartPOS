package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

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
//@Table(
//		name = "YSY_USER_MST",
//	    uniqueConstraints = {
//	        @UniqueConstraint(
//	            name = "YSY_USER_MST_contstraintName",
//	            columnNames = {"USER_ID", "BIZ_CD"}
//	        )
//	    }
//	   )
@Table(name = "YSY_USER_MST")
@Entity
public class YsyUser extends BaseEntity implements Serializable{

	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	//String username으로 한 이유는 스프링 정책상 변수를 맞춰주기 위해 , 다른걸로 하려면 셋팅을 좀 해야해서 그냥 db컬럼명을 지정해주고 처리함. 패스워드도 동일한 이유임.
	@Column(name = "USER_ID",length = 255 , nullable = false , unique = true)
	private String username;
	@Column(name = "USER_PW" , length = 255 , nullable = false)
	private String password;
	@Column(name = "USER_NM" , length = 255 , nullable = false)
	private String name;
	@Column(name = "USER_ADDR")
	private String addr;
	@Column(name = "USER_EMAIL")
	private String email;
	@Column(name = "USER_PHONE")
	private String phone;
	@Column(name = "ROLES")
	private String roles;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BIZ_CD" , referencedColumnName="BIZ_CD" , nullable = false)
	private YsyBiz ysyBiz; 
	
	
	public List<String> getRoleList(){
		if(this.roles.length() > 0)
			return Arrays.asList(roles.split(",")) ;
		else
			return new ArrayList<String>();
	}
}
