package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "YSY_USER_DTL")
@Entity
public class YsyUserDetail extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	  
	@EmbeddedId
	private PK pk;
	
	@Column(name="S_TIME" )
	protected LocalDateTime startTime;
	@Column(name="E_TIME" )
	protected LocalDateTime endTime;
	@Column(name="USER_GRADE" , length = 50)
	private String grade;
	@Column(name="USER_POINT" )
	private double point;
	@Column(name="USER_COUPON" , length = 250)
	private String coupon;//,,,,,

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID" , referencedColumnName="USER_ID", updatable = false)
	private YsyUserMst ysyUserMst1;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Embeddable
	public static class PK implements Serializable{
		
		

		private String userId;
		
		@Column(name = "USER_DTL_ID")
		private String userDetailId;
	}
}
