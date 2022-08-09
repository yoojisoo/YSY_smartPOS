package com.ysy.jwt.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.ysy.jwt.auth.dto.BoardDto;
import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.entity.QYsyBoardFile;
import com.ysy.jwt.auth.entity.QYsyBoardMst;
import com.ysy.jwt.auth.entity.YsyBoardMst;

import io.swagger.annotations.ApiOperation;

/**
 * @Path : com.ysy.jwt.auth.service
 * @Author : clubbboy@naver.com
 * @Date   : 2022. 8. 3.
 * @Desc :
 */
@Service
public class YsyBoardService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JPAQueryFactory query = new JPAQueryFactory(em);
	
	private QYsyBoardMst  qYsyBoardMst  = QYsyBoardMst.ysyBoardMst; 
	private QYsyBoardFile qYsyBoardFile = QYsyBoardFile.ysyBoardFile; 
	
	
	@Autowired
	private YsyFlieService ysyFlieService;
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 3. - 오후 1:12:18
	 * @YsyBoardService - getBoardList
	 * @return 
	 * @Return Type : ResponseAuthDto<List<BoardDto>>
	 * @Desc : admin free board 조회 
	 */
	@ApiOperation(value = "admin free board 조회"
			     ,notes = "")
	@Transactional
	public ResponseAuthDto<BoardDto> getBoardList(String loginUserId) {
		List<BoardDto> ysyBoardList =
				query.select(qYsyBoardMst)
					 .from(qYsyBoardMst)
					 .where(qYsyBoardMst.useYn.eq("Y"))
					 .orderBy(qYsyBoardMst.boardId.desc())
					 .fetch()
					 .stream()
					 .map(ysyBoardMst -> new BoardDto(ysyBoardMst, loginUserId))
					 .collect(Collectors.toList());
		
		return new ResponseAuthDto<BoardDto>(ysyBoardList , HttpStatus.OK);
	}
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 3. - 오전 10:33:42
	 * @YsyBoardService - saveYsyBoard
	 * @param boardDto
	 * @param userId
	 * @param isThumbnail
	 * @return 
	 * @Return Type : ResponseAuthDto<String>
	 * @Desc : admin free board 저장 - 파일도 있으면 같이 저장해줌.
	 */
	@Transactional
	public ResponseAuthDto<String> saveYsyBoard(BoardDto boardDto ,  String userId) {
		long boardId = query.select(qYsyBoardMst.boardId.max().coalesce(0L))
			 	 			.from(qYsyBoardMst)
			 	 			.where(qYsyBoardMst.useYn.eq("Y"))
			 	 			.fetchOne() +1
			 	   ;
		System.out.println("boardId = "+boardId);
		int cnt = em.createNativeQuery("INSERT INTO "
					+ "ysy_board_mst (board_id , user_id , title , content , reg_id , reg_dt)"
					+ "       VALUES (       ? ,       ? ,     ? ,       ? ,      ? , now() )")
				    .setParameter(1, boardId)
				    .setParameter(2, userId)
				    .setParameter(3, boardDto.getTitle())
				    .setParameter(4, boardDto.getContent())
				  	.setParameter(5, userId)
				  	.executeUpdate();
		
		
		if(boardDto.getFiles().size() > 0) {
			int fileCnt = ysyFlieService.saveYsyBoardFile(boardDto, boardId, userId);
			if(fileCnt <= 0) {
				em.getTransaction().rollback();
				return new ResponseAuthDto<String>("error","file save error" , HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseAuthDto<String>("save ok" , HttpStatus.OK);
		
	}
	
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 3. - 오후 1:12:51
	 * @YsyBoardService - getBoardDetail
	 * @param boardId 
	 * @Return Type : void
	 * @Desc :
	 */
	@ApiOperation(value = "admin free board 상세 조회"
		     ,notes = "")
	@Transactional
	public void getBoardDetail(String boardId) {
		List<YsyBoardMst> ysyBoardList 
			= query.select(qYsyBoardMst)
				   .from(qYsyBoardMst)
				   .fetch();
	}


	
	@Transactional
	public void modifyYsyBoard(BoardDto boardDto) {
		//새로운 파일이 올라오면 기존파일과 비교하고 없는것만 저장시킴 
		//기존에 파일있던게 삭제 되었는지 확인하여 파일 지워줌.
		//
//		ysyFlieService
//		qYsyBoardMst
//		qYsyBoardFile
		
		boardDto.getBoardId();//board에 기존 내용 업데이트
		boardDto.getFileDtoList().size();//기존 파일 삭제 여부 - db랑 비교하여 기존파일 없는건 삭제함
		boardDto.getFiles();//새롭게 올린 파일 기존 폴더에 이미지 추가 후 db에 저장
		
//		JPAUpdateClause update = new JPAUpdateClause(em, qYsyBoardMst);
//		
//		update.set(qYsyBoardMst.title, boardDto.getTitle())
//		.set(null, null)
		
		YsyBoardMst ysyBoardMst = query.select(qYsyBoardMst)
		.from(qYsyBoardMst)
		.where(qYsyBoardMst.boardId.eq(boardDto.getBoardId()))
		.fetchOne();
		
		ysyBoardMst.setTitle(boardDto.getTitle());
		
	}
	
	
	

}
