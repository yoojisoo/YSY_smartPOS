package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
		name = "YSY_GRP_MST",
	    uniqueConstraints = {
	        @UniqueConstraint(
	            name = "YSY_GRP_MST_contstraintName",
	            columnNames = {"GRP_ID", "BIZ_CD"}
	        )
	    }
	   )
@Entity
public class YsyGrp  extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
//	@Enumerated
	@Column(name = "GRP_ID" , length = 50 , nullable = false )
	private String grpId;
	@Column(name = "GRP_NM" , length= 50 )
	private String grpNm;
	@Column(name = "USE_YN" , length = 1  )
	private String useYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BIZ_CD",referencedColumnName="BIZ_CD" , nullable = false )
	private YsyBiz ysyBiz; 
	
}
