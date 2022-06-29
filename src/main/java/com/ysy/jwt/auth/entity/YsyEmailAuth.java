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


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "YSY_EMAIL_AUTH")
@Entity
public class YsyEmailAuth extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "TMP_EMAIL" , length =250)
	private String tmpEmail;
	@Column(name = "TMP_EMAIL_KEY" , length = 10)
	private String tmpEmailKey;

}
