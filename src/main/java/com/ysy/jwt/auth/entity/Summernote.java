package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author clubbboy@naver.com
 *
 * summerNote test
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Summernote extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String title;
	
	@Lob
	private String content;
}
