package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity
public class YsyScheduleMst extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "S_ID")
	private long sId;
	
	@Column(name = "TITLE" , nullable=false)
	private String title;
	
	@Comment(value = "요약 간단 설명")
	@Column(name = "SUB_TITLE")
	private String subTitle;
	
	@Column(name = "CONTENT")
	@Lob
	private String content;
	
	@Comment(value = "예약 구분 - 포장 , 홀 , 등등...")
	@Column(name = "GUBUN")
	private String gubun;
	
	@Comment(value = "스케쥴 시작 일자 시분까지")
	@Column(name = "S_DATE")
	private LocalDateTime sDate;

	@Comment(value = "스케쥴 종료 일자 시분까지")
	@Column(name = "E_DATE")
	private LocalDateTime eDate;
	
	@Comment(value = "공개여부")
	@Column(name = "IS_PUBLIC")
	private boolean  isPublic;
	
	@Comment(value = "ui표현 색상")
	@Column(name = "COLOR")
	private String color;
	
	@Comment(value = "예약된 table 정보")
	@Column(name = "TABLE_ID")
	private String tableId;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = YsyUserMst.class)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID" , updatable = false)
    @JsonBackReference
    private YsyUserMst ysyUserMst;
	
	
}
