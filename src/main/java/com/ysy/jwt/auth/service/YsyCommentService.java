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
 * @Date   : 2022. 8. 10.
 * @Desc : admin board comment service
 */
@Service
public class YsyCommentService {

	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JPAQueryFactory query = new JPAQueryFactory(em);

	
	
	
	@Transactional
	public int saveYsyBoardComment(long boardId , String userId , String content)  {
		
		try 
		{
			int cnt = em.createNativeQuery("INSERT INTO "
					  + "ysy_board_comment( board_id , user_id , content , reg_id , reg_dt)"
					  + "            VALUES (?,?,?,?,now() )")
					     .setParameter(1, boardId)
					     .setParameter(2, userId)
					     .setParameter(3, content)
					     .setParameter(4, userId)
						 .executeUpdate();
			return cnt;
		} 
		catch (Exception e) 
		{
			return -1;
		}
	}
	@Transactional
	public int saveYsyBoardComment(long boardId , long parentId , String userId , String content)  {
		
		try 
		{
			int cnt = em.createNativeQuery("INSERT INTO "
					+ "ysy_board_comment( board_id , user_id , content , reg_id , reg_dt)"
					+ "            VALUES (?,?,?,?,now() )")
					.setParameter(1, boardId)
					.setParameter(2, userId)
					.setParameter(3, content)
					.setParameter(4, userId)
					.executeUpdate();
			return cnt;
		} 
		catch (Exception e) 
		{
			return -1;
		}
	}
	
	
	
	
}
