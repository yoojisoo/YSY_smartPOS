package com.ysy.jwt.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ysy.jwt.auth.dto.BoardDto;
import com.ysy.jwt.auth.dto.FileDto;
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.handler.YsyFlieHandler;
import com.ysy.jwt.auth.model.PrincipalDetails;
import com.ysy.jwt.auth.service.YsyBoardService;

import io.swagger.annotations.ApiOperation;


/**
 * @Path : com.ysy.jwt.auth.controller
 * @Author : clubbboy@naver.com
 * @Date   : 2022. 8. 3.
 * @Desc : admin free board controller - admin만 작성 가능 
 */
@RestController
@RequestMapping("/ysy/v1/admin")
public class YsyBoardController {
	
	@Autowired
	private YsyBoardService ysyBoardService;

	
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 3. - 오후 2:56:04
	 * @YsyBoardController - getYsyBoardList
	 * @return 
	 * @Return Type : ResponseAuthDto<List<BoardDto>>
	 * @Desc :
	 */
	@ApiOperation(value = "get 방식 admin만 사용할 수 있는 board" 
		        , notes = " 보여주는건 다 보여줄까? get쪽은 권한 아직 안넣음.")
	@GetMapping("/getYsyBoardList")
	public ResponseAuthDto<BoardDto> getYsyBoardList() {
		
		return ysyBoardService.getBoardList();
	}
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 3. - 오전 10:48:48
	 * @YsyBoardController - saveYsyBoard
	 * @param boardDto
	 * @param p
	 * @return
	 * @throws Exception 
	 * @Return Type : ResponseAuthDto<String>
	 * @Desc : admin free board 신규 저장 - 파일 업로드 가능.
	 */
	@PostMapping(value="/saveYsyBoard" , consumes = {"multipart/form-data"})
	public ResponseAuthDto<String> saveYsyBoard(BoardDto boardDto , @AuthenticationPrincipal PrincipalDetails p) throws Exception {
//		System.out.println("title="+dto.getTitle());
//		System.out.println("content="+dto.getContent());
		if(p.getUser() == null) return null;
		
		if(boardDto.getTitle() == null || boardDto.getTitle().isEmpty()) {
			new Exception("제목이 없습니다.");
		}
		for(MultipartFile file :boardDto.getFiles()) {
			System.out.println("getOriginalFilename = "+file.getOriginalFilename());
			System.out.println("getName             = "+file.getName());
			System.out.println("getSize             = "+file.getSize());
			System.out.println("getContentType      = "+file.getContentType());
			System.out.println("");
		}

//		return "test";
		return ysyBoardService.saveYsyBoard(boardDto , p.getUser().getUsername());
		
	}
	
}
