package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "YSY_BIZ_MST")
@Entity
public class YsyBizMst extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private long id;

	@Id
	@Column(name="BIZ_CD" , length=50 , nullable = false)
	private String bizCd;
	@Column(name="BIZ_NM" , length=50 )
	private String bizNm;
	@Column(name="USE_YN" , length=1 )
	private String useYn;
	@Column(name="DEL_YN" , length=1 )
	private String delYn;
}
