package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name = "YSY_BTN_MST")
@Entity
public class YsyBtnMst extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "BTN_ID" )
	private String btnId;

	@Column(name = "BTN_NM" )
	private String btnNm;
	
	@Column(name = "BTN_SORT")
	private int btnSort;
	
	@Column(name = "USE_YN" , nullable = false , length = 1)
	private String useYn;
	
	
}
