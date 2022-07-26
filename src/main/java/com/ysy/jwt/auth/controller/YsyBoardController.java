package com.ysy.jwt.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysy.jwt.auth.dto.BoardDto;
import com.ysy.jwt.auth.service.YsyBoardService;

import io.swagger.annotations.ApiOperation;
/**
 * @author clubbboy@naver.com
 *  2022. 7. 23.
 *  Desc : ysy board controller
 *  admin만 사용 가능한 
 */
@RestController
@RequestMapping("/ysy/v1")
public class YsyBoardController {
	
	@Autowired
	private YsyBoardService ysyBoardService;
	
	
	@ApiOperation(value = "get 방식 admin만 사용할 수 있는 board" 
			    , notes = " 보여주는건 다 보여줄까? get쪽은 권한 아직 안넣음.")
	@GetMapping("/getYsyBoardList")
	public void getYsyBoardList() {
		
	}
	@ApiOperation(value = "get 방식 admin만 사용할 수 있는 board" 
			    , notes = " 보여주는건 다 보여줄까? get쪽은 권한 아직 안넣음. \n  board 상세 보기 또는 수정 후 board 1건 get")
	@GetMapping("/getYsyBoard")
	public void getYsyBoard(String boardId) {
		
	}
	@ApiOperation(value = "get 방식 commentList  param = BoardId" 
			    , notes = " 전체 comment list - board 1건 가져갈때 가져가는게 나을까?? 아직 필요한지 모르것음. ")
	@GetMapping("/getYsyCommentList")
	public void getCommentList(String boardId) {
		
	}
	@ApiOperation(value = "get 방식 commentList  param = BoardId" 
			    , notes = " comment 수정 후 comment 1건 get ")
	@GetMapping("/getYsyComment")
	public void getComment(String commentId) {
		
	}
	
	
	
	
	
	@ApiOperation(value = "multipart 저장 (YsyBoardMst)" 
			    , notes = "image and text data 전송 , Dto 확인바람 \n 저장은 권한 적용해야해 \n 주소에 admin 아직 적용 안됨")
	@PostMapping("/setYsyBoard")
	public void setYsyBoard() {
		
	}
	@ApiOperation(value = "multipart 수정 (YsyBoardMst)" 
			    , notes = "image and text data 전송 , Dto 확인바람 \n 수정은 권한은 작성자만 적용해야해 \n 주소에 admin 아직 적용 안됨")
	@PostMapping("/modYsyBoard")
	public void modYsyBoard(String BoardId) {
		
	}
	@ApiOperation(value = "multipart 삭제 (YsyBoardMst)" 
			    , notes = "image and text data 전송 , Dto 확인바람 \n 삭제은 권한은 작성자만 적용해야해 \n 주소에 admin 아직 적용 안됨")
	@PostMapping("/delYsyBoard")
	public void delYsyBoard(String BoardId) {
		
	}
	@ApiOperation(value = "comment 삭제 (YsyBoardComment)" 
			, notes = "comment dto 만들어야함. \n 수정은 권한은 작성자만 적용해야해 \n 주소에 admin 아직 적용 안됨")
	@PostMapping("/delYsyComment")
	public void delYsyComment(String BoardId) {
		
	}
	
	
	
	
	
	
	
	@PostMapping("/createSummerNote")
	public String createSummerNote(@RequestBody BoardDto dto) {
//		System.out.println("title="+dto.getTitle());
//		System.out.println("content="+dto.getContent());
		
		return ysyBoardService.createSummerNote(dto.getContent());
		
	}

	
	
	
	
	
}
