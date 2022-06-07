package com.ysy.jwt.auth.entity.base;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Comment;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity {

	
	@Column(name="REG_ID" ,updatable = false , length = 100)
	@Comment(value = "입력자")
	public String regId;
	
	@Column(name="REG_DT" ,updatable = false )
	@Comment(value = "입력일자")
    protected LocalDateTime regDt;
	
	
	@Column(name="MOD_ID"  , length = 100)
	@Comment(value = "수정자")
	protected String modId;
	
	@Column(name="MOD_DT" )
	@Comment(value = "수정 일자")
	protected LocalDateTime modDt;
	
	@Column(name="ROW_DESC")
	@Comment(value = "상속받은 테이블 행의 Description")
	protected String desc;
	
	@PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        regDt = now;
        modDt = now;
    }
	
	@PreUpdate
    public void preUpdate() {
		modDt = LocalDateTime.now();
    }
}
