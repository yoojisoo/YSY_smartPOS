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

	private String orgFileName;
	private String newFileName;
	private String filePath;
	private String fileFullPath;
	private long fileSize;
}
