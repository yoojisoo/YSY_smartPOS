package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Path : com.ysy.jwt.auth.entity
 * @Author : clubbboy@naver.com
 * @Date   : 2022. 8. 11.
 * @Desc : admin 일정 관리 테이블
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_SCHEDULE_MST")
//@Entity
public class YsyScheduleMst extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "S_ID")
	private long sId;
	
	@Column(name = "S_TITLE")
	private String sTitle;
	@Column(name = "S_CONTENT")
	@Lob
	private String sContent;
	
	@Comment(value = "스케쥴 시작 일자 시분까지")
	@Column(name = "S_DATE")
	private LocalDateTime sDate;
	@Comment(value = "스케쥴 종료 일자 시분까지")
	@Column(name = "E_DATE")
	private LocalDateTime eDate;
	@Column(name = "IS_PUBLIC")
	private boolean  isPublic;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = YsyUserMst.class)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID" , updatable = false)
    @JsonBackReference
    private YsyUserMst ysyUserMst;
	
	
}
