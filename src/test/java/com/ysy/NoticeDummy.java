package com.ysy;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ysy.biz.entity.SystemNotice;
import com.ysy.biz.repository.SystemNoticeRepository;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NoticeDummy {

	@Autowired
	private SystemNoticeRepository systemNoticeRepository;
	
	@Autowired
	private YsyUserMstRepository ysyUserMstRepository;
	
	@Test
	public void addSystemNotice() {
		System.out.println("system notice dummy sample data start =========================");
		
//		YsyUserMst userInfo = ysyUserMstRepository.findByUsername("s_plus7@naver.com");
		List<YsyUserMst> userMstList = ysyUserMstRepository.findAll();
		
		List<String> titleList = Arrays.asList("1.0.1 업데이트", "test title1", "test title2", "회원가입 안내", "시스템 에러 안내");
//		List<String> regIdList = Arrays.asList("s_plus7", "mnew2m", "clubbboy", "admin1", "mng96");
//		List<String> usernameList = Arrays.asList("s_plus7@naver.com", "mnew2m@gmail.com", "clubbboy@naver.com", "test1@naver.com", "test2@naver.com");
		
//		String content1 = "떠나요 둘이서 모든 걸 훌훌 버리고\r\n"
//					+ "		제주도 푸른 밤 그 별 아래\r\n"
//					+ "		이제는 더이상 얽매이긴 우리 싫어요\r\n"
//					+ "		신문에 TV에 월급봉투에\r\n"
//					+ "		아파트 담벼락보다는\r\n"
//					+ "		바달 볼수 있는 창문이 좋아요\r\n"
//					+ "		낑깡밭 일구고 감귤도 우리 둘이 가꿔봐요\r\n"
//					+ "		정말로 그대가 외롭다고 느껴진다면\r\n"
//					+ "		떠나요 제주도 푸른 밤 하늘 아래로\r\n"
//					+ "		떠나요 둘이서 힘들게 별로 없어요\r\n"
//					+ "		제주도 푸른밤 그 별아래\r\n"
//					+ "		그동안 우리는 오랫동안 지쳤잖아요\r\n"
//					+ "		술집에 카페에 많은 사람에\r\n"
//					+ "		도시의 침묵보다는 바다의 속삭임이 좋아요\r\n"
//					+ "		신혼부부 밀려와 똑같은 사진 찍기 구경하며\r\n"
//					+ "		정말로 그대가 재미없다 느껴진다면\r\n"
//					+ "		떠나요 제주도 푸르메가 살고 있는 곳\r\n"
//					+ "		도시의 침묵보다는 바다의 속삭임이 좋아요\r\n"
//					+ "		신혼부부 밀려와 똑같은 사진 찍기 구경하며\r\n"
//					+ "		정말로 그대가 재미없다 느껴진다면\r\n"
//					+ "		떠나요 제주도 푸르메가 살고 있는 곳";
//		
//		byte[] bytesContent = content1.getBytes();
//		Blob blob = null;
//		try {
//			blob = new SerialBlob(bytesContent);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		for(int i=0; i<=titleList.size(); i++) {
//			YsyUserMst ysyUserMst = new YsyUserMst();
//			ysyUserMst.setUsername("");
//			ysyUserMst.setName("");
			
			SystemNotice systemNotice = SystemNotice.builder()
					.content(null)
					.title(titleList.get(i))
					.ysyUserMst(userMstList.get(i))
					.build();
			systemNoticeRepository.save(systemNotice);
		}
		
		System.out.println("system notice dummy sample data end =========================");
		
		
	}
		
}
