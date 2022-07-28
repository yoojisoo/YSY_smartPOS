package com.ysy.jwt.auth.dto;

import java.util.ArrayList;
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
public class BoardDto {
	private long   boardId;
	private String title;
	private String content;
	private String useYn;
	private String writer;
	private List<MultipartFile> files = new ArrayList<>();
//	private MultipartFile files;
}
