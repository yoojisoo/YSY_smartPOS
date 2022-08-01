package com.ysy.jwt.auth.service;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ysy.jwt.auth.entity.QYsyUserMst;
import com.ysy.jwt.auth.entity.QYsyUserRTokenMap;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.entity.YsyUserRTokenMap;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;
import com.ysy.jwt.auth.repository.YsyUserRTokenMapRepository;

@Configuration
public class JwtService {

	public final String SECRET              = "ysyFirstPjt";   // JWT Token 생성시 서버만 알고있는 비밀 값 , 이 값으로 복호화 함.
	public final long   EXPIRATION_TIME     = 1000*1*60*600L;   // (1/1000초) -> 1초 단위 (1000*1  수정X)
	public final long   EXPIRATION_TIME_RE  = 1000*60*60*24L;  // (1/1000초) -> 1분 단위 (1000*60 수정X)
	public final String TOKEN_PREFIX        = "Bearer ";       // token 생성 후 client 전송시 token앞에 붙을 값. 인증시에 이 값으로 1차 검사 진행
//	public final String HEADER_STRING       = "access_token";  // Client 전송시 token에 대한 key Authorization
	public final String HEADER_STRING       = "authorization";  // Client 전송시 token에 대한 key Authorization
	public final String HEADER_REFRESH      = "refresh_token"; // Client 전송시 token에 대한 key
	public final String HEADER_USER_NAME    = "user_name";

	@Autowired
	private YsyUserRTokenMapRepository ysyUserRTokenMapRepository;
	@Autowired
	private YsyUserMstRepository ysyUserMstRepository;
	
	private QYsyUserRTokenMap qYsyUserRTokenMap = QYsyUserRTokenMap.ysyUserRTokenMap; 
	private QYsyUserMst qYsyUserMst = QYsyUserMst.ysyUserMst; 
	
	@PersistenceContext
	EntityManager em; // 1
	
	/** access token create  */
	public String createJwtAccessToken(String userId , String name) {
		if(userId == null || userId.isEmpty()) return "";
		
		Long curMill = System.currentTimeMillis();
		Long expiration = curMill+EXPIRATION_TIME;
		System.out.println("createJwtAccessToken = "+curMill);
		System.out.println("createJwtAccessToken = "+expiration);
		String jwtToken = JWT.create()
				.withSubject("jwtToken")
				.withExpiresAt(new Date(expiration))
				.withClaim("tokenType", "access_token")
				.withClaim("name"    , name)
				.withClaim("username", userId)
				.sign(Algorithm.HMAC512(SECRET));
		tokenExpirationCheck(jwtToken);
		return jwtToken;
	}
	
	/** refresh token create  */
	public String createJwtRefreshToken(String userId , String name) {
		if(userId == null || userId.isEmpty()) return "";
		Long curMill = System.currentTimeMillis();
		Long expiration = curMill+EXPIRATION_TIME_RE;
		System.out.println("createJwtRefreshToken = "+curMill);
		System.out.println("createJwtRefreshToken = "+expiration);
		
		String jwtToken = JWT.create()
				.withSubject("jwtToken")
				.withExpiresAt(new Date(expiration))
				.withClaim("tokenType", "refresh_token")
				.withClaim("name"    , name)// 이름
				.withClaim("username", userId)//id
				.sign(Algorithm.HMAC512(SECRET+"refresh"));
		
		return jwtToken;
	}
	
	/** client로 token 전송 */
	public String tokenSend(HttpServletResponse response , String jwtToken , String jwtTokenRe) {
		String msg = "ok";
		try {
			
			saveRefreshToken(jwtTokenRe);
			System.out.println("TOKEN_PREFIX+jwtToken=>\n"+TOKEN_PREFIX+jwtToken);
			System.out.println("TOKEN_PREFIX+jwtTokenRe=>\n"+TOKEN_PREFIX+jwtTokenRe);
			response.addHeader(HEADER_STRING  , TOKEN_PREFIX+jwtToken);
			response.addHeader(HEADER_REFRESH , TOKEN_PREFIX+jwtTokenRe);
			response.addHeader("state","200");
		} catch (Exception e) {
			e.printStackTrace();
			msg = "fail";
		}
		return msg;
	}
	
	@Transactional
	public void saveRefreshToken(String jwtTokenRe) {
		try 
		{
			String                     userId = getClaim(jwtTokenRe , "username" , true);
			YsyUserMst             ysyUserMst = ysyUserMstRepository.findById(userId)
					.orElseThrow(()->  new IllegalArgumentException("id가 존재하지 않습니다.") );
			
			YsyUserRTokenMap ysyUserRTokenMap = YsyUserRTokenMap.builder()
																.userId(userId)
																.refreshToken(jwtTokenRe)
																.build();
			ysyUserRTokenMapRepository.delete(ysyUserRTokenMap);
			
			ysyUserRTokenMapRepository.save(ysyUserRTokenMap);
			
			System.out.println("refresh token save = " + jwtTokenRe);
			
		} 
		catch (Exception e) 
		{
			System.out.println("saveRefreshToken error");
			e.printStackTrace();
		}
	}
	
	/** token 안의 claim 값 return */
	public String getClaim(String token , String key ,boolean isRefresh) {
		if(token == null || token.isEmpty()) return "";
		String secretKey = isRefresh ? SECRET+"refresh":SECRET;
				
		
		String result = JWT.require(Algorithm.HMAC512(secretKey))
				.build()
				.verify(token)
				.getClaim(key)//"username"
				.asString();
		
		return result;
	}
	/** token 안의 claim 값 return */
	public String getClaim(String token , String key) {
		
		DecodedJWT decodedJWT = JWT.decode(token);
		
		return decodedJWT.getClaim(key).asString();
	}
	
	/** token 만료시간 체크 : access_token 만료시간 지났으면 true로 나옴. */
	public boolean tokenExpirationCheck(String token) {
		DecodedJWT decodedJWT = JWT.decode(token);
		System.out.println(decodedJWT.getClaims().toString());
		System.out.println(decodedJWT.getExpiresAt());
		
		Calendar cal = Calendar.getInstance();
		Date expiration =  decodedJWT.getExpiresAt();
		Date cueDate = cal.getTime();
		
		System.out.println("expiration = "+expiration);
		System.out.println("cueDate = "+cueDate);
		
		return expiration.before(cueDate);
		 
	}
	
	//get refresh token 
	@Nullable
	public String getRefershToken(String userId) {
		JPAQueryFactory query = new JPAQueryFactory(em); 
		YsyUserRTokenMap result = query
				.select(qYsyUserRTokenMap)
				.from(qYsyUserRTokenMap)
				.innerJoin(qYsyUserMst).fetchJoin()
				.on(qYsyUserRTokenMap.ysyUserMst.username.eq(qYsyUserMst.username))
				.where(qYsyUserRTokenMap.ysyUserMst.username.eq(userId))
				.limit(1)
				.fetchOne();
		
		return result == null ? "" : result.getYsyUserMst().getUsername();
	}
	
	
}
