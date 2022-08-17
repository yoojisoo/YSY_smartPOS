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
	private long   boardId; // 게시판 아이디
	private String userId; // 작성자 아이디  
	private String userName; // 작성자 이름
	private String writer;//작성자 아이디
	private String title; // 제목
	private String subTitle;// 설명
	private String content; // 내용
	private int    viewCnt; // 조회수
	private boolean modify;//수정여부
	
	private int attechFileCnt;//첨부파일 갯수
	private int commentCnt;//댓글수
	
	/** board 저장시 파일이 있을경우 client에서 전송된 파일이 여기로 담김. */
	private List<MultipartFile> files = new ArrayList<>();
	
	/** board 조회시 파일이 같이 있을경우 파일정보 담아서 클라이언트에 내려주는 dto */
	private List<FileDto> fileDtoList = new ArrayList<>();
	
	
	
	/**
	 * @Create By : clubbboy@naver.com
	 * @Date : 2022. 8. 3.
	 * @param boardMst
	 * @Desc : admin free board에서 보드 상세 페이지 조회시 client전송을 위한 boardDto 생성자.
	 */
	public BoardDto(YsyBoardMst boardMst , String loginUserId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		this.boardId        = boardMst.getBoardId();
		this.userName       = boardMst.getYsyUserMst().getName();
		this.userId         = boardMst.getYsyUserMst().getUsername();
		this.writer         = boardMst.getYsyUserMst().getUsername();
		this.title          = boardMst.getTitle();
		this.subTitle       = boardMst.getSubTitle();
		this.content        = boardMst.getContent();
		this.attechFileCnt  = boardMst.getFileList().size();
		this.commentCnt     = boardMst.getCommentList().size();
		
		if(this.userId.equals(loginUserId)) modify = true;
		
		this.useYn = boardMst.getUseYn();
		this.regId = boardMst.getRegId();
		this.regDt = boardMst.getRegDt() != null?boardMst.getRegDt().format(formatter): "";
		this.modId = boardMst.getModId();
		this.modDt = boardMst.getModDt() != null?boardMst.getModDt().format(formatter):"";
		
		for(YsyBoardFile file : boardMst.getFileList()) {
			fileDtoList.add(
				FileDto.builder()
				       .fileId(file.getFileId())
				       .newFileName(file.getNewFileName())
				       .orgFileName(file.getOrgFileName())
				       .folderName(file.getFolderName())
				       .filePath(file.getFilePath())
				       .fileFullPath(file.getFileFullPath())
				       .fileSize(file.getFileSize())
					   .build()
			);
		}
	}
	
	
	
}
