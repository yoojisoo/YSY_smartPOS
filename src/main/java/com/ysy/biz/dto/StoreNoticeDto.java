package com.ysy.biz.dto;

import com.ysy.biz.entity.StoreNotice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 22-07-05 mnew2m 스토어 공지사항 DTO 추가 */
public class StoreNoticeDto {

	int no;
	int boardId;
	String title;
	String writer;
	
	public StoreNoticeDto(StoreNotice notice , int index) {
		this.no      = index;
		this.boardId = (int)notice.getBoardId();
		this.title   = notice.getTitle();
		this.writer  = notice.getModId() != null ? notice.getModId() : notice.getRegId();
	};
}
