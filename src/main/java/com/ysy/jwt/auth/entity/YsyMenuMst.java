package com.ysy.jwt.auth.entity;

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
//@Table(name = "YSY_MENU_MST")
//@Entity
public class YsyMenuMst extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="MENU_ID" , length=50 , unique = true , nullable = false)
	private String menuId;
	@Column(name="MENU_NM" , length=50 , unique = true , nullable = false)
	private String menuNm;
	@Column(name="MENU_PATH" , length=50 , unique = true , nullable = false)
	private String menuPath;
	
}
