package com.ysy.jwt.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 *  Desc : admin만 사용하는 자유 게시판 Comment
 *  댓글의 댓글은 어떻게 설계해야할까?
 *  
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_BOARD_COMMENT")
@Entity
public class YsyBoardComment extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;
	
	@Comment(value="Comment 내용")
	@Column(name = "CONTENT", nullable = false)
	@Lob
	private String content;
	
	
	@Comment(value="삭제유무")
	@Column(name = "IS_DEL" , length = 1)
	private String isDel;
	
	
	@Comment(value="Comment 부모 Board")
	@ManyToOne
    @JoinColumn(name = "BOARD_ID" , referencedColumnName = "BOARD_ID" )
    private YsyBoardMst ysyBoardMst;
	
	@Comment(value="Comment 작성자")
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = YsyUserMst.class)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID" , updatable = false)
    @JsonBackReference
    private YsyUserMst ysyUserMst;
	
	
	
	
	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private YsyBoardComment parent;
	
	@OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<YsyBoardComment> children = new ArrayList<>();
	
}
