package com.ysy.jwt.auth.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.dto.BoardDto;
import com.ysy.jwt.auth.dto.FileDto;
import com.ysy.jwt.auth.handler.YsyFlieHandler;


/**
 * @Path : com.ysy.jwt.auth.service
 * @Author : clubbboy@naver.com
 * @Date   : 2022. 8. 3.
 * @Desc : 첨부파일 처리 클래스
 *  첨부파일을 image폴더에 저장하고 그에 해당하는 경로를 db로 저장함.
 */
@Service
public class YsyFlieService {

	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JPAQueryFactory query = new JPAQueryFactory(em);
	
	@Autowired
	private YsyFlieHandler ysyFlieHandler;
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 3. - 오전 10:34:14
	 * @YsyFlieService - saveYsyBoardFile
	 * @param boardDto
	 * @param boardId
	 * @param userId
	 * @return 
	 * @Return Type : int
	 * @Desc :  첨부파일로 올라온 파일 경로 db에 저장 
	 */
	@Transactional
	public int saveYsyBoardFile(BoardDto boardDto , long boardId , String userId)  {
		
		try 
		{
			List<FileDto> fileDtoList = ysyFlieHandler.saveFiles(boardDto);
			
//			if(isThumbnail == null || isThumbnail.isEmpty()) isThumbnail = "N";
			int cnt = 0;
			int idx = 0;
			for(FileDto dto : fileDtoList) {
				cnt =+ em.createNativeQuery("INSERT INTO "
						 + "ysy_board_file ( board_id  , org_file_name , new_file_name , file_path , file_full_path  "
						 + "               , file_size , file_idx      , is_thumbnail  , reg_id    , reg_dt)"
						 + "        VALUES (?,?,?,?,?  ,?,?,?,?,now() )")
					     .setParameter(1, boardId)
					     .setParameter(2, dto.getOrgFileName())
					     .setParameter(3, dto.getNewFileName())
						 .setParameter(4, dto.getFilePath())
						 .setParameter(5, dto.getFileFullPath())
						 .setParameter(6, dto.getFileSize())
						 .setParameter(7, idx)
						 .setParameter(8, idx == 0? "Y":"N")
						 .setParameter(9, userId)
						 .executeUpdate();
				idx++;
			}
			return cnt;
		} 
		catch (Exception e) 
		{
			// db저장이 잘못되었을 경우 기존 저장했던 파일을 삭제해줌.
			ysyFlieHandler.deleteSaveFile(null);
		}
		
		return -1;
		
	}
}
