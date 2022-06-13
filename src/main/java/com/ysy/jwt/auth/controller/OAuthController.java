package com.ysy.jwt.auth.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysy.jwt.auth.dto.JoinDto;
import com.ysy.jwt.auth.filter.JwtProperties;
import com.ysy.jwt.auth.model.KakaoOAuthToken;
import com.ysy.jwt.auth.model.KakaoProfile;
import com.ysy.jwt.auth.model.PrincipalDetails;
import com.ysy.jwt.auth.service.YsyUserMstService;

import lombok.Data;
/**
 * @author clubbboy@naver.com
 *  2022. 5. 28.
 *  Desc :
 *  
 *  resapi key : 4c9e081b17404f289741f6792bd4c6e7

		카카오로그인 활성화
		Redirect URI 로그인 요청 콜백 : http://localhost:8000/ysy/v1/auth/kakao/redirect
		Logout Redirect URI : http://localhost:8000/ysy/v1/auth/kakao/logout
 */
//@Controller
@RestController
@RequestMapping("/ysy/v1/auth/kakao")
//@RequiredArgsConstructor
public class OAuthController {

	@Autowired
	private  AuthenticationManager authenticationManager;
	
	@Autowired
	private YsyUserMstService ysyUserMstService; 
	
	private static String KAKAO_PWD = "kakao_login_tmp_password";
	
	
	private final String kakao_Content_type = "application/x-www-form-urlencoded;charset=utf-8";
	private final String kakao_client_id    = "4c9e081b17404f289741f6792bd4c6e7";
	private final String kakao_grant_type   = "authorization_code";
//	private final String kakao_redirect_uri = SystemConfig.SEVER_URL + "/ysy/v1/auth/kakao/redirect";
	private final String kakao_redirect_uri = "http://localhost:8080/kakaoLogin";
	private final String kakao_auth_addr    = "https://kauth.kakao.com/oauth/token";
	
	
	private final String kakao_auth_before  = "Bearer ";
	private final String kakao_profile_url  = "https://kapi.kakao.com/v2/user/me";
	
	
	
	@Data
	public static class KakaoCode{
		String code;
		String path ;
	}
	
	//kakao 인증 콜백
	@PostMapping("/setCode")
	public String setCode(@RequestBody KakaoCode kCode , HttpServletResponse response) {
		
		String access_token = getKakaoAuth(kCode.getCode());
		
		KakaoProfile profile = getKakaoProfile(access_token);
		
		String msg = kakaoUserSave(profile);
		
		/** kakao login시 강제로 토큰 생성해서 클라이언트에 전송함. */
		if(msg.equals("ok")) {
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(
							profile.getKakao_account().getEmail(),
							KAKAO_PWD); 
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			System.out.println("principalDetails.getUser().getUsername() = > "+principalDetails.getUser().getUsername());
//			SecurityContext context = SecurityContextHolder.createEmptyContext(); 
//			context.setAuthentication(authentication);
			
			String jwtToken = JWT.create()
					.withSubject("jwtToken")
					.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
					.withClaim("name"    , profile.getKakao_account().getProfile().getNickname())
					.withClaim("username", profile.getKakao_account().getEmail())
					.sign(Algorithm.HMAC512(JwtProperties.SECRET));
			
			String jwtTokenRe = JWT.create()
					.withSubject("jwtToken")
					.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME_RE))
					.withClaim("name"    , profile.getKakao_account().getProfile().getNickname())
					.withClaim("username", profile.getKakao_account().getEmail())
					.sign(Algorithm.HMAC512(JwtProperties.SECRET+"refresh"));
			
			
			response.addHeader(JwtProperties.HEADER_STRING  , JwtProperties.TOKEN_PREFIX+jwtToken);
			response.addHeader(JwtProperties.HEADER_REFRESH , JwtProperties.TOKEN_PREFIX+jwtTokenRe);
			response.addHeader("state","200");
			
			return "ok";
		}
		
		return msg;
	}
	
	
	/* token 받기
	 * POST /oauth/token HTTP/1.1
		Host: kauth.kakao.com
		Content-type: application/x-www-form-urlencoded;charset=utf-8
		<<params>>
		1.grant_type	String	authorization_code로 고정
		2.client_id	String	앱 REST API 키 [내 애플리케이션] > [앱 키]에서 확인 가능
		3.redirect_uri	String	인가 코드가 리다이렉트된 URI
		4.code	String	인가 코드 받기 요청으로 얻은 인가 코드
		//5번은 off로 해놨기 때문에 필수 아님. 
		5.client_secret	String	토큰 발급 시, 보안을 강화하기 위해 추가 확인하는 코드
          [내 애플리케이션] > [보안]에서 설정 가능 ON 상태인 경우 필수 설정해야 함	X
	 *  https://kauth.kakao.com/oauth/token
	 *  
	 *  인증받은 access_token return
	 * */
	public String getKakaoAuth(String code) {
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", kakao_Content_type);
		
		MultiValueMap<String , String > params = new LinkedMultiValueMap<String ,String>();
		params.add("grant_type"  , kakao_grant_type);
		params.add("client_id"   , kakao_client_id);
		params.add("redirect_uri", kakao_redirect_uri);
		params.add("code"        , code);
		
		HttpEntity<MultiValueMap<String,String>> kakaoTokenReq = new HttpEntity<>(params,headers);
		
		ResponseEntity<String> response =  rt.exchange(kakao_auth_addr, HttpMethod.POST, kakaoTokenReq, String.class) ;
		
		System.out.println("getKakaoAuth response.getBody = "+response.getBody());
		
		ObjectMapper mapper = new ObjectMapper();
		KakaoOAuthToken oToken = null;
		try {
			oToken = mapper.readValue(response.getBody(), KakaoOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("oToken.getAccess_token() = "+oToken.getAccess_token());
		
		return oToken.getAccess_token();
	}
	
	
	/** 사용자 정보 요청
	 * Request: 액세스 토큰 사용
	 * GET/POST /v2/user/me HTTP/1.1
		Host: kapi.kakao.com
		Authorization: Bearer ${ACCESS_TOKEN}
		Content-type: application/x-www-form-urlencoded;charset=utf-8
		
		Header
		 - Authorization	사용자 인증 수단, 액세스 토큰 값
							Authorization: Bearer ${ACCESS_TOKEN}
		Parameter
	 * 
	 *  
	 * */
	public KakaoProfile getKakaoProfile(String accessToken) {
		
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", kakao_Content_type);
		headers.add("Authorization", kakao_auth_before + accessToken);
		
		
		HttpEntity<MultiValueMap<String,String>> kakaoInfoReq = new HttpEntity<>(headers);
		
		ResponseEntity<String> response =  rt.exchange(kakao_profile_url, HttpMethod.POST, kakaoInfoReq, String.class) ;
		
		System.out.println("KakaoProfile response.getBody = "+response.getBody());
		
		/** jsonschema2pojo 
		 * */
		ObjectMapper mapper = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = mapper.readValue(response.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("email = "+kakaoProfile.getKakao_account().getEmail());
		System.out.println("kakaoInfo.getProperties() = "+kakaoProfile.getProperties().getNickname());
		System.out.println("kakaoInfo.getKakao_account() = "+kakaoProfile.getKakao_account().getProfile().getNickname());
		
		return kakaoProfile;
	}
	
	
	/** 카카오 정보 가져와서 db에 저장 후 token 생성하여 클라이언트로 보내줌.
	 * */
	public String kakaoUserSave(KakaoProfile profile) {
		
		
		JoinDto joinDto = JoinDto.builder()
				.bizCd("0001")//카카오 로그인 전 비즈코드 받는 부분 필수.
				.username(profile.getKakao_account().getEmail())
				.password(KAKAO_PWD)//default password -> 가입 후 변경 요망.
				.name(profile.getKakao_account().getProfile().getNickname())
				.oAuthPath("kakao")
				.build();
		String msg = ysyUserMstService.signUp(joinDto);
		
		return msg;
	}
	
	@GetMapping("logout")
	public String kakaoLogout() {
		
		
		return "logout";
	}
	
	
}
