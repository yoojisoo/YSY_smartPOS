package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * @author clubbboy@naver.com
 *  2022. 7. 23.
 *  Desc : admin만 사용하는 자유 게시판
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_BOARD_MST")
@Entity
public class YsyBoardMst extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long boardId;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = YsyUserMst.class)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID" , updatable = false)
    @JsonBackReference
    private YsyUserMst ysyUserMst;

    @Column(name = "TITLE",nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    @Lob
    private String content;
    
    @Comment(value="조회수")
    @Column(name = "VIEW_CNT",nullable = false)
    private int viewCnt;

    
    /** File 처리 관련 table */
    @OneToMany(
    	   mappedBy = "ysyBoardMst",
    	   cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    	   orphanRemoval = true
    )
    private List<YsyBoardFile> fileList = new ArrayList<>();

    /** Comment 처리 관련 table */
    @OneToMany(
    		mappedBy = "ysyUserMst",
    		cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    		orphanRemoval = true
    		)
    private List<YsyBoardComment> commentList = new ArrayList<>();

    
    
    
    
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
 // Board에서 파일 처리 위함
    public void addFile(YsyBoardFile ysyBoardFile) {
        this.fileList.add(ysyBoardFile);

	// 게시글에 파일이 저장되어있지 않은 경우
        if(ysyBoardFile.getYsyBoardMst() != this)
            // 파일 저장
        	ysyBoardFile.setYsyBoardMst(this);
    }
	
    //comment 처리
    public void addComment(YsyBoardComment ysyBoardComment) {
    	this.commentList.add(ysyBoardComment);
    	
    	// 게시글에 파일이 저장되어있지 않은 경우
    	if(ysyBoardComment.getYsyBoardMst() != this)
    		// 파일 저장
    		ysyBoardComment.setYsyBoardMst(this);
    }
    
}
