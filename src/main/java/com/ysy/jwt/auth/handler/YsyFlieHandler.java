package com.ysy.jwt.auth.handler;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ysy.jwt.auth.dto.BoardDto;
/**
 * 
 * @author clubbboy@naver.com
 * board 저장시 파일 있을경우 파일은 해당 앱의 images폴더에 저장
 *
 */
@Component
public class YsyFlieHandler {

	
//	private final PhotoService photoService;
//
//    public FileHandler(PhotoService photoService) {
//        this.photoService = photoService;
//    }

    public List parseFileInfo(BoardDto boardDto)throws Exception {
    	
    	// 반환할 파일 리스트
        List fileList = new ArrayList<>();
        
        List <String> fileNames = new ArrayList<>();
        
        //exception 발생시 파일 지울 리스트
        List<MultipartFile> files = boardDto.getFiles();
        String delFullPath = "";
    	
    	try {
    		// 전달되어 온 파일이 존재할 경우
            if(!CollectionUtils.isEmpty(files)) 
            { 
                // 파일명을 업로드 한 날짜로 변환하여 저장
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
                String current_date = now.format(dateTimeFormatter);
      
                // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
                // 경로 구분자 File.separator 사용 
                String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
                System.out.println("absolutePath => " + absolutePath);

                // 파일을 저장할 세부 경로 지정
                String path = "images" + File.separator + current_date;
                File file = new File(path);
      
                // 디렉터리가 존재하지 않을 경우
                if(!file.exists()) {
                    boolean wasSuccessful = file.mkdirs();
      
    	            // 디렉터리 생성에 실패했을 경우
    	            if(!wasSuccessful)
    	                System.out.println("file: was not successful");
                }

                // 다중 파일 처리
                for(MultipartFile multipartFile : files) {
      
                        // 파일의 확장자 추출
                        String originalFileExtension;
                        String contentType = multipartFile.getContentType();

                        // 확장자명이 존재하지 않을 경우 처리 x
                        if(ObjectUtils.isEmpty(contentType)) {
                            throw new Exception("error => 파일 확장자가 존재하지 않음!!!!");
                        }
                        else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
                            if(contentType.contains("image/jpeg"))
                                originalFileExtension = ".jpg";
                            else if(contentType.contains("image/png"))
                                originalFileExtension = ".png";
                            else if(contentType.contains("image/gif"))
                            	originalFileExtension = ".gif";
                            else  // 다른 확장자일 경우 처리 x
                            	throw new Exception("error => 지원하지 않는 확장자임!!!!");
                        }
                    
                        
                        String orgFileName = multipartFile.getOriginalFilename();
                        //파일명 중복 피하고자 나노초까지 얻어와 지정
                        String new_file_name =  System.nanoTime() + "_" + orgFileName;
                        String      filePath = path + File.separator + new_file_name;
                        String      fullPath = absolutePath + filePath;
                        long        fileSize = multipartFile.getSize();
                        
                        
                        delFullPath = absolutePath + path + File.separator;
                        // 파일 DTO 이용하여 Photo 엔티티 생성
//                        YsyBoardFile ysyBoardFile = new YsyBoardFile(
//                                photoDto.getOrigFileName(),
//                                photoDto.getFilePath(),
//                                photoDto.getFileSize()
//                        );
    //  
//                        // 생성 후 리스트에 추가
//                        fileList.add(photo);
      
                        // 업로드 한 파일 데이터를 지정한 파일에 저장
                        file = new File(fullPath);
                        multipartFile.transferTo(file);
                        
                        //exception 발생시 기존 저장했던 파일들 다 지우기 위해 
                        fileNames.add(new_file_name);
                        
                        
                        // 파일 권한 설정(쓰기, 읽기)
                        file.setWritable(true);
                        file.setReadable(true);
                }
            }
		} catch (Exception e) {
			System.out.println("file save Exception!!!!!!!!!!!!!!!!!!!!!!!!" + e.getMessage());
			File delFile = null;
			try {
				for(String filename : fileNames) {
					String path = delFullPath + filename;
					delFile = new File(path);
					if( delFile.exists() ){
			    		if(delFile.delete()){
			    			System.out.println("delete file success = " + path);
			    		}else{
			    			System.out.println("delete file fail!!");
			    		}
			    	}else{
			    		System.out.println("file is not found!! = " + path);
			    	}
				}
			e.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
  
        

        return fileList;
    }
}
