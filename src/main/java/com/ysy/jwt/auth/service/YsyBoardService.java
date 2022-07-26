package com.ysy.jwt.auth.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.entity.QSummernote;
import com.ysy.jwt.auth.entity.Summernote;

@Service
public class YsyBoardService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	JPAQueryFactory query = new JPAQueryFactory(em);
	QSummernote qSummernote = QSummernote.summernote;
	
	
//	public void getYsyBoardList() {
//
//	}
//
//	public void getYsyBoard(String boardId) {
//
//	}
//
//	public void getCommentList(String boardId) {
//
//	}
//
//	public void getComment(String commentId) {
//
//	}
//
//	public void setYsyBoard() {
//
//	}
//
//	public void modYsyBoard(String BoardId) {
//
//	}
//
//	public void delYsyBoard(String BoardId) {
//
//	}
//
//	public void delYsyComment(String BoardId) {
//
//	}
	
	
	
	@Transactional
	public String createSummerNote(String content) {
		
		
		em.createNativeQuery("INSERT INTO "
				+ "summernote (title , content , reg_id , reg_dt)"
				+ "VALUES (?,?,'admin'  , now())")
		  .setParameter(1, "")
		  .setParameter(2, content)
		  .executeUpdate();
		
		
		Summernote res =  query.select(qSummernote)
			.from(qSummernote)
			.where(qSummernote.id.eq(22l))
			.fetchOne();
		
//		System.out.println(res.getContent());
		System.out.println(res.toString());
		
		return res.getContent();
	}
}
