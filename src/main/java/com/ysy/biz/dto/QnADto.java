package com.ysy.biz.dto;

import com.ysy.biz.entity.QnA;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/** 22-07-28 mnew2m QnA DTO 추가 */
public class QnADto {

	int no;
	int boardId;
	String title;
	String writer;
	
	public QnADto(QnA qna , int index) {
		this.no      = index;
		this.boardId = (int)qna.getBoardId();
		this.title   = qna.getTitle();
		this.writer  = qna.getModId() != null ? qna.getModId() : qna.getRegId();
	};
}
