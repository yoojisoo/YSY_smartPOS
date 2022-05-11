package com.ysy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ysy.jwt.auth.entity.YsyUser;

@Table(name = "SAMPLE_FAVORITE_MST")
@Entity
public class Favorite {

	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@Column(name ="MENU_NAME" , length = 255 , nullable = false)
	private String menuName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID" , referencedColumnName="USER_ID" , nullable = false)
	private YsyUser ysyUser;
	
}
