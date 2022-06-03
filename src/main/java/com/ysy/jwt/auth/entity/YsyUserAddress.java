package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "YSY_USER_ADDRESS")
@Entity
public class YsyUserAddress extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "ADDR_TYPE")
	private String addrType;
	@Column(name = "ADDR_ZIP_CODE")
	private String addrZipCode;
	@Column(name = "ADDR_CITY")
	private String addrCity;
	@Column(name = "ADDR_DETAIL")
	private String addrDetail;
	@Column(name = "ADDR_ETC")
	private String addrEtc;
	@Column(name = "PHONE1")
	private String phone1;
	@Column(name = "PHONE2")
	private String phone2;
	
	@JoinColumn(name = "USER_ID" , referencedColumnName = "USER_ID")
	@ManyToOne
//	@JsonIgnoreProperties//해당 어노테이션은 무한반복을 안되게 함.
	@JsonManagedReference // 순환참조 방지
	private YsyUserMst ysyUserMst; 
}
