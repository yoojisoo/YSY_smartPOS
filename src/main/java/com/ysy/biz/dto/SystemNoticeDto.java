package com.ysy.biz.dto;

import com.ysy.biz.entity.SystemNoticeMst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemNoticeDto {

	int no;
	int boardId;
	String title;
	String writer;
	
	public SystemNoticeDto(SystemNoticeMst notice , int index) {
		this.no = index;
		this.boardId = (int)notice.getBoardId();
		this.title = notice.getTitle();
		this.writer = notice.getModId() != null ? notice.getModId() : notice.getRegId();
	};
}
