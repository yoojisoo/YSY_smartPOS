package com.ysy.jwt.auth.dto;

import lombok.Data;

@Data
public class BaseDto {

	protected String regId; 
	protected String regDt; 
	protected String modId; 
	protected String modDt; 
	protected String useYn;
}
