package com.ysy.jwt.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import com.ysy.jwt.auth.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author clubbboy@naver.com
 *  2022. 7. 23.
 *  Desc : admin만 사용하는 자유 게시판의 사진
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "YSY_BOARD_FILE")
@Entity
public class YsyBoardFile extends BaseEntity implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long fileId;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID" , referencedColumnName = "BOARD_ID" )
    private YsyBoardMst ysyBoardMst;

    @Comment(value="파일 서버 저장시 파일명 중복을 피하기 위해 새로운 값을 원본파일명과 병합하여 생성")
    @Column(name = "NEW_FILE_NAME" , nullable = false)
    private String newFileName;  // 파일 원본명

    @Comment(value = "user가 올린 실질적인 파일명")
    @Column(name = "ORG_FILE_NAME" , nullable = false) 
    private String orgFileName;  // 파일 원본명

    @Comment(value = "파일이 올라갈 위치")
    @Column(name = "FOLDER_NAME" , nullable = false) 
    private String folderName;  // 파일 원본명

    @Column(name = "FILE_PATH" ,nullable = false)
    private String filePath;  // 파일 저장 경로

    @Column(name = "FILE_FULL_PATH" ,nullable = false)
    private String fileFullPath;  // 파일 저장 경로

    @Column(name = "FILE_SIZE" )
    private Long fileSize;
   
    @Comment(value="대표 이미지 여부")
    @Column(name = "IS_THUMBNAIL" )
    private String isThumbnail;
    
    @Comment(value="파일 순서")
    @Column(name = "FILE_IDX" )
    private int fileIdx;

    
    // Board 정보 저장
    public void setBoard(YsyBoardMst ysyBoardMst){
        this.ysyBoardMst = ysyBoardMst;

	// 게시글에 현재 파일이 존재하지 않는다면
        if(!ysyBoardMst.getFileList().contains(this))
            // 파일 추가
        	ysyBoardMst.getFileList().add(this);
    }
}
