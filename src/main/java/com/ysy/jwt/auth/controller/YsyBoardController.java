package com.ysy.jwt.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ysy.jwt.auth.dto.BoardDto;
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.model.PrincipalDetails;
import com.ysy.jwt.auth.service.YsyBoardService;
import com.ysy.jwt.auth.service.YsyFlieService;

import io.swagger.annotations.ApiOperation;


/**
 * @Path : com.ysy.jwt.auth.controller
 * @Author : clubbboy@naver.com
 * @Date   : 2022. 8. 3.
 * @Desc : admin free board controller - admin만 작성 가능 
 */
@RestController
@RequestMapping("/ysy/v1")
public class YsyBoardController {
	
	@Autowired
	private YsyBoardService ysyBoardService;
	@Autowired
	private YsyFlieService ysyFlieService;
	
	
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 3. - 오후 2:56:04
	 * @YsyBoardController - getYsyBoardList
	 * @return 
	 * @Return Type : ResponseAuthDto<List<BoardDto>>
	 * @Desc : admin board 조회
	 */
	@ApiOperation(value = "get 방식 admin만 사용할 수 있는 board list 조회" 
		        , notes = "Role 권한이 ADMIN인 사람만 이용가능. \n"
		        		+ "아직 검색조건은 넣지 않았음.")
	@GetMapping("/admin/getYsyBoardList")
	public ResponseAuthDto<BoardDto> getYsyBoardList(@AuthenticationPrincipal PrincipalDetails p) {
		
		return ysyBoardService.getBoardList(p.getUsername());
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
	@ApiOperation(value = "신규 board 저장" , notes = "Role 권한이 ADMIN인 사람만 이용가능. ")
	@PostMapping(value="/admin/saveYsyBoard" , consumes = {"multipart/form-data"})
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
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 4. - 오전 10:38:27
	 * @YsyBoardController - downloadFile
	 * @param boardId
	 * @param fileId
	 * @param fileName
	 * @return 
	 * @Return Type : ResponseEntity<Resource>
	 * @Desc : 파일 단건 다운로드시
	 */
	@ApiOperation(value = "board 조회시 첨부파일 다운로드 - 단건씩만 가능" , notes = "Role 권한이 ADMIN인 사람만 이용가능. ")
	@GetMapping(value="/admin/downloadYsyBoardFile")
	public ResponseEntity<Resource> downloadYsyBoardFile(@RequestParam long boardId 
			                                            ,@RequestParam long fileId 
			                                            ,@RequestParam String fileName) {
		System.out.println("boardId  = "+boardId);
		System.out.println("fileId   = "+fileId);
		System.out.println("fileName = "+fileName);
		return ysyFlieService.downloadYsyBoardFile(boardId, fileId, fileName);
	}
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 4. - 오전 10:42:59
	 * @YsyBoardController - downloadFiles
	 * @param boardId
	 * @return 
	 * @Return Type : ResponseEntity<List<Resource>>
	 * @Desc : boardId에 걸려있는 모든 파일 다운로드시  - 아직 안됨... 클라이언트 처리가 안되었음 찾아봐야함.
	 */
	@ApiOperation(value = "board 조회시 board에 첨부된 모든파일 다운로드 - client쪽 아직 구현 안되었음." , notes = "Role 권한이 ADMIN인 사람만 이용가능. ")
	@GetMapping(value="/admin/downloadYsyBoardFiles")
	public ResponseEntity<List<Resource>> downloadYsyBoardFiles(long boardId ) {
		
		return ysyFlieService.getYsyBoardAllFiles(boardId);
	}
	
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 4. - 오후 5:46:18
	 * @YsyBoardController - modifyYsyBoard
	 * @param boardDto
	 * @param p 
	 * @Return Type : void
	 * @Desc : admin board 수정 . 파일 처리도 해야함.
	 */
	@PostMapping(value="/admin/modifyYsyBoard" , consumes = {"multipart/form-data"})
	public ResponseAuthDto<String> modifyYsyBoard(BoardDto boardDto ,@AuthenticationPrincipal PrincipalDetails p)  throws Exception{
		
		if(boardDto.getUserId().equals(p.getUsername())) {
			return ysyBoardService.modifyYsyBoard(boardDto , p.getUsername());
		}
		
		return new ResponseAuthDto<String>("error","writer error" , HttpStatus.UNAUTHORIZED);
	}
	
	//ysy board delete 
	public ResponseAuthDto<String> deleteYsyBoard(BoardDto boardDto , @AuthenticationPrincipal PrincipalDetails p) {
		
		if(boardDto.getUserId().equals(p.getUsername())) {
			return ysyBoardService.deleteYsyBoard(boardDto , p.getUsername());
		}
		
		return new ResponseAuthDto<String>("error","writer error" , HttpStatus.UNAUTHORIZED);
	}
	
	//조회수 업데이트
	public int updateYsyBoardViewCnt(long BoardId) {
		return ysyBoardService.updateYsyBoardViewCnt(BoardId);
	}
	
}
