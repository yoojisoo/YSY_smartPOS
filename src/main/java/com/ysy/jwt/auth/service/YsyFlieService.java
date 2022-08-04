package com.ysy.jwt.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.dto.BoardDto;
import com.ysy.jwt.auth.dto.FileDto;
import com.ysy.jwt.auth.entity.QYsyBoardFile;
import com.ysy.jwt.auth.entity.YsyBoardFile;
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
	
	
	private QYsyBoardFile qYsyBoardFile = QYsyBoardFile.ysyBoardFile;
	
	
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
						 + "ysy_board_file ( board_id       , org_file_name , new_file_name , folder_name   , file_path "
						 + "               , file_full_path , file_size     , file_idx      , is_thumbnail  , reg_id    , reg_dt)"
						 + "        VALUES (?,?,?,?,?  ,?,?,?,?,?,now() )")
					     .setParameter(1, boardId)
					     .setParameter(2, dto.getOrgFileName())
					     .setParameter(3, dto.getNewFileName())
					     .setParameter(4, dto.getFolderName())
						 .setParameter(5, dto.getFilePath())
						 .setParameter(6, dto.getFileFullPath())
						 .setParameter(7, dto.getFileSize())
						 .setParameter(8, idx)
						 .setParameter(9, idx == 0? "Y":"N")
						 .setParameter(10, userId)
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
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 4. - 오전 10:23:14
	 * @YsyFlieService - getYsyBoardFile
	 * @param boardId
	 * @param fileName
	 * @return 
	 * @Return Type : YsyBoardFile
	 * @Desc : 파일명에 의한 file 저장 정보 가져옴
	 */
	public ResponseEntity<Resource> downloadYsyBoardFile(long boardId , long fileId , String fileName) {
		YsyBoardFile fileInfo = 
			   query.select(qYsyBoardFile)
			 	 	.from(qYsyBoardFile)
			 	 	.where(qYsyBoardFile.ysyBoardMst.boardId.eq(boardId)
			 	 		  ,qYsyBoardFile.fileId.eq(fileId)
			 	 		  ,qYsyBoardFile.newFileName.eq(fileName))
			 	 	.fetchOne();
		;
		
		Resource resourceFile = ysyFlieHandler.downFile(fileInfo.getFolderName() , fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfo.getOrgFileName() + "\"").body(resourceFile);
	}
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 4. - 오전 10:26:04
	 * @YsyFlieService - getYsyBoardFile
	 * @param fileId
	 * @return 
	 * @Return Type : List<YsyBoardFile>
	 * @Desc : 파일 전체 다운로드시 전체 저장된 파일에 대한 저장정보 가져옴
	 */
	public ResponseEntity<List<Resource>> getYsyBoardAllFiles(long boardId ) {
		
		List<YsyBoardFile> fileInfoList = 
		   query.select(qYsyBoardFile)
				.from(qYsyBoardFile)
				.where(qYsyBoardFile.ysyBoardMst.boardId.eq(boardId))
				.orderBy(qYsyBoardFile.fileIdx.asc())
				.fetch();
		List<Resource> downFileList = new ArrayList<>();
		for(YsyBoardFile fileInfo : fileInfoList) {
			downFileList.add(ysyFlieHandler.downFile(fileInfo.getFolderName() , fileInfo.getNewFileName()));
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=files").body(downFileList);
	}
}
