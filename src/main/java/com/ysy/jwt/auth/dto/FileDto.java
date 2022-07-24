package com.ysy.jwt.auth.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

	
	private List<MultipartFile> files;
	private String field1;
	private String field2;
	private String field3;
}
