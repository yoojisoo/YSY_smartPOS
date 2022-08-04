package com.ysy.jwt.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {
	
	private long fileId;
	private String orgFileName;// 파일 원본명
	private String newFileName;// 파일 변환 명
	private String folderName;//저장될 폴더 명 - default 일자
	private String filePath; // 파일 경로 
	private String fileFullPath;//파일 풀 경로
	private long fileSize; // 파일 사이즈
}
