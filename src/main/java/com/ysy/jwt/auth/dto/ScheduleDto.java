package com.ysy.jwt.auth.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {

	
	private String sId;
	private String gubun;
	private String title;
	private String subTitle;
	private String content;
	private String writer;
	
	private boolean isPublic;
	private String tableId;
	
	private String sDate;
	private String eDate;
	private String color;
	private String regDt;
	private String regId;
	private String modDt;
	private String modId;
	
	
	
}
