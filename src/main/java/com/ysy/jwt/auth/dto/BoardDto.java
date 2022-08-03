package com.ysy.jwt.auth.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ysy.jwt.auth.entity.YsyBoardFile;
import com.ysy.jwt.auth.entity.YsyBoardMst;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Path : com.ysy.jwt.auth.dto
 * @Author : clubbboy@naver.com
 * @Date   : 2022. 8. 3.
 * @Desc : board 저장 또는 클라이언트에 데이터 전송시 사용하는 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto extends BaseDto{
	private long   boardId;
	private String userId; // 작성자 아이디  
	private String writer;//작성자 - 
	private String title;
	private String content;
	private int    viewCnt;
	
	private int attechFileSize;
	
	
	private List<MultipartFile> files = new ArrayList<>();
	
	private List<FileDto> fileDtoList = new ArrayList<>();
	
	
	/**
	 * @Create By : clubbboy@naver.com
	 * @Date : 2022. 8. 3.
	 * @param boardMst
	 * @Desc : admin free board client전송을 위한 boardDto 생성자.
	 */
	public BoardDto(YsyBoardMst boardMst) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		this.boardId        = boardMst.getBoardId();
		this.userId         = boardMst.getYsyUserMst().getName();
		this.writer         = boardMst.getYsyUserMst().getUsername();
		this.title          = boardMst.getTitle();
		this.content        = boardMst.getContent();
		this.attechFileSize = boardMst.getFileList().size();
		
		this.useYn = boardMst.getUseYn();
		this.regId = boardMst.getRegId();
		this.regDt = boardMst.getRegDt() != null?boardMst.getRegDt().format(formatter): "";
		this.modId = boardMst.getModId();
		this.modDt = boardMst.getModDt() != null?boardMst.getModDt().format(formatter):"";
		
//		for(YsyBoardFile file : boardMst.getFileList()) {
//			fileDtoList.add(
//				FileDto.builder()
//				       .fileId(file.getFileId())
//				       .newFileName(file.getNewFileName())
//				       .orgFileName(file.getOrgFileName())
//				       .filePath(file.getFilePath())
//				       .fileFullPath(file.getFileFullPath())
//					   .build()
//			);
//		}
	}
	
	
	
}
