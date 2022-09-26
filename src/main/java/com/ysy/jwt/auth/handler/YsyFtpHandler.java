package com.ysy.jwt.auth.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Component
@Data
public class YsyFtpHandler {
	
	private static final String FTP_IP   = "ysy899.cdn1.cafe24.com";
	private static final int    FTP_PORT = 21;
	private static final String FTP_ID   = "ysy899";
	private static final String FTP_PWD  = "YSY899!@";
	private static final String FTP_DIR  = "/www/2022_08_17";
	
	private FTPClient ftpClient;
	
	
	public YsyFtpHandler() {
		System.out.println("###################################################### YsyFtpHandler");
		System.out.println("###################################################### YsyFtpHandler");
		System.out.println("###################################################### YsyFtpHandler");
		System.out.println("###################################################### YsyFtpHandler");
		System.out.println("###################################################### YsyFtpHandler");
		System.out.println("###################################################### YsyFtpHandler");
		this.ftpClient = new FTPClient();
		this.ftpClient.setControlEncoding("euc-kr");
	}
	
	// FTP 연결 및 설정
	// ip : FTP IP, port : FTP port, id : FTP login Id, pw : FTP login pw, dir : FTP
	// Upload Path
	public void connect(String ip, int port, String id, String pw, String dir) throws Exception {
		
		if(ip == null   || ip.isEmpty())  ip   = FTP_IP;
		if(id == null   || id.isEmpty())  id   = FTP_ID;
		if(pw == null   || pw.isEmpty())  pw   = FTP_PWD;
		if(dir == null  || dir.isEmpty()) dir  = FTP_DIR;
		if(port >= 0)                     port = FTP_PORT;
		
		String ftp_info = " ip =["+ip +"] port["+port+"] id =["+id +"] pwd["+pw+"] dir["+dir+"]";
		
		try {
			boolean result = false;
			ftpClient.connect(ip, port); // FTP 연결
			ftpClient.setControlEncoding("UTF-8"); // FTP 인코딩 설정
			int reply = ftpClient.getReplyCode(); // 응답코드 받기

			if (!FTPReply.isPositiveCompletion(reply)) { // 응답 False인 경우 연결 해제
				ftpClient.disconnect();
				throw new Exception("FTP서버 연결실패 connect info :" + ftp_info);
			}
			if (!ftpClient.login(id, pw)) {
				ftpClient.logout();
				throw new Exception("FTP서버 로그인실패 connect info :" + ftp_info);
			}

			ftpClient.setSoTimeout(1000 * 10); // Timeout 설정
			ftpClient.login(id, pw); // FTP 로그인
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 파일타입설정
			ftpClient.enterLocalPassiveMode(); // Active 모드 설정
			result = ftpClient.changeWorkingDirectory(dir); // 저장파일경로

			if (!result) { // result = False 는 저장파일경로가 존재하지 않음
				ftpClient.makeDirectory(dir); // 저장파일경로 생성
				ftpClient.changeWorkingDirectory(dir);
			}
		} catch (Exception e) {
			if (e.getMessage().indexOf("refused") != -1) {
				throw new Exception("FTP서버 연결실패");
			}
			throw new Exception("FTP서버 연결실패 connect info :" + ftp_info);
		}
	}
	
	
	// FTP 연결해제
	public void disconnect() {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// FTP 파일 업로드
	public void storeFile(String saveFileNm, InputStream inputStream) throws Exception {
		try {
			if (!ftpClient.storeFile(FTP_DIR+"/"+saveFileNm, inputStream)) {
				throw new Exception("FTP서버 업로드실패");
			}
		} catch (Exception e) {
			if (e.getMessage().indexOf("not open") != -1) {
				throw new Exception("FTP서버 연결실패");
			}
			throw e;
		}
	}
	
	FileInputStream fis = null;
	public void ftpFileUpload(List<MultipartFile> mf) {
		
		try {
			connect(FTP_IP, FTP_PORT, FTP_ID, FTP_PWD, FTP_DIR);

			try {
				String filename = null;
				// 멀티파일 개수만큼 파일명 체크 후 업로드
				for(MultipartFile multipartFile : mf) {
					filename = multipartFile.getOriginalFilename();

					// MultipartFile to FileInputStream
					File f = new File(multipartFile.getOriginalFilename());
//					File f = new File("C:\\Users\\clubb\\Downloads\\262832165620200_영업비밀1 - 복사본 (2) (1).jpg");
					multipartFile.transferTo(f);
//					f.createNewFile();
					fis = new FileInputStream(f);
					storeFile(filename, fis); // 파일명
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					fis.close();
				}
			}

			disconnect();

		} 
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
