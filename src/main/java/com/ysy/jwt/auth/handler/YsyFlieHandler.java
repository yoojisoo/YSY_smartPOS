package com.ysy.jwt.auth.handler;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ysy.jwt.auth.dto.BoardDto;
import com.ysy.jwt.auth.dto.FileDto;

import lombok.Data;

/**
 * @Path : com.ysy.jwt.auth.handler
 * @Author : clubbboy@naver.com
 * @Date   : 2022. 8. 3.
 * @Desc : board 저장시 파일 있을경우 파일은 해당 앱의 images폴더에 현재날짜 폴더에 저장
 */
@Component
@Data
public class YsyFlieHandler {

	
	//현재 저장된 파일명 리스트
	private List <String> saveFileNames = null;
	
	//현재 저장된 파일 풀 경로
	private String saveFullPath = "";
	
	//폴더명
	private String preFoldNm = "images";
	
	/**
	 * @Create by   : clubbboy@naver.com
	 * @Create date : 2022. 8. 3. - 오전 10:34:23
	 * @YsyFlieHandler - saveFiles
	 * @param boardDto
	 * @return
	 * @throws Exception 
	 * @Return Type : List<FileDto>
	 * @Desc :전달된 boardDto의 파일여부 판단 후 파일을 해당 프로젝트 경로의 images라는 폴더에 저장한다
	 *  db에는 경로를 저장하고 실제 파일은 폴더에 저장.
	 */
    public List<FileDto> saveFiles(BoardDto boardDto)throws Exception {
        
        //exception 발생시 파일 지울 리스트
        List<MultipartFile> files = boardDto.getFiles();
    	
        return saveFiles(files);
    }
    
    /**
     * @Create by   : clubbboy@naver.com
     * @Create date : 2022. 8. 3. - 오전 10:41:51
     * @YsyFlieHandler - saveFiles
     * @param List<MultipartFile> files
     * @return
     * @throws Exception 
     * @Return Type : List<FileDto>
     * @Desc : 전달된 boardDto의 파일여부 판단 후 파일을 해당 프로젝트 경로의 images라는 폴더에 저장한다
	 *  db에는 경로를 저장하고 실제 파일은 폴더에 저장.
     */
    public List<FileDto> saveFiles(List<MultipartFile> files)throws Exception{
    	// 반환할 파일 리스트
        List<FileDto> fileList = new ArrayList<>();
        
        saveFileNames = new ArrayList<>();
    	
    	try 
    	{
    		// 전달되어 온 파일이 존재할 경우
            if(!CollectionUtils.isEmpty(files)) 
            { 
                // 파일명을 업로드 한 날짜로 변환하여 저장
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
                String current_date = now.format(dateTimeFormatter);
      
                // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
                // 경로 구분자 File.separator 사용 
                String absolutePath = new File("").getAbsolutePath() + File.separator;
                System.out.println("absolutePath => " + absolutePath);

                // 파일을 저장할 세부 경로 지정
                String path = preFoldNm + File.separator + current_date;
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
                        
                        
                        saveFullPath = absolutePath + path + File.separator;
                        
                        FileDto fileDto = FileDto.builder()
                        		.orgFileName(orgFileName)
                        		.newFileName(new_file_name)
                        		.folderName(current_date)
                        		.filePath(filePath)
                        		.fileFullPath(fullPath)
                        		.fileSize(fileSize)
                        		.build();
                        
                        
      
                        // 업로드 한 파일 데이터를 지정한 파일에 저장
                        file = new File(fullPath);
                        multipartFile.transferTo(file);
                        
                        //exception 발생시 기존 저장했던 파일들 다 지우기 위해 
                        saveFileNames.add(new_file_name);
                        
                        
                        // 파일 권한 설정(쓰기, 읽기)
                        file.setWritable(true);
                        file.setReadable(true);
                        
                        fileList.add(fileDto);
                }
            }
            
            return fileList;
		} 
    	catch (Exception e) 
    	{
			System.out.println("file save Exception!!!!!!!!!!!!!!!!!!!!!!!!" + e.getMessage());
			deleteSaveFile(null , null);
			e.printStackTrace();
		}
    	return null;
    }
    
    
    /**
    * @Create by   : clubbboy@naver.com
    * @Create date : 2022. 8. 3. - 오전 10:34:33
    * @YsyFlieHandler - deleteSaveFile
    * @param List<String> fileNames
    * @return 
    * @Return Type : boolean
    * @Desc : 파일 저장 후 저장이 잘못되었거나 db에 파일 경로가 저장이 안되었을 경우 기존 저장했던 파일을 모두 삭제한다.
    */
    public boolean deleteSaveFile(List<String> fileNames , String pullPath) {
    	
    	if(fileNames == null || fileNames.size() == 0)
    	{
    		fileNames = saveFileNames;
    	}
    	
    	if(pullPath == null || pullPath.isEmpty()) {
    		pullPath = saveFullPath;
    	}
    	
    	File delFile = null;
		try {
			for(String filename : saveFileNames) {
				String path = pullPath + filename;
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
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    
    
    
    /**
     * @Create by   : clubbboy@naver.com
     * @Create date : 2022. 8. 4. - 오후 4:27:48
     * @YsyFlieHandler - downFile
     * @param folderName
     * @param fileName
     * @return 
     * @Return Type : Resource
     * @Desc : 서버에 저장된 파일을 찾아서 return.
     */
    public Resource downFile(String folderName , String fileName)  {
    	String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
    	String path         = preFoldNm + File.separator + folderName;
    	String filePath     = absolutePath + path + File.separator + fileName;
		try {
			Path file = Paths.get(filePath);
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error downFile : " + e.getMessage());
		}
    	
//    	return null;
    }
}
