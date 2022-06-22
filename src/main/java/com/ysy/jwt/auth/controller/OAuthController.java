package com.ysy.jwt.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysy.jwt.auth.dto.JoinDto;
import com.ysy.jwt.auth.model.OAuthTokenModel;
import com.ysy.jwt.auth.model.PrincipalDetails;
import com.ysy.jwt.auth.model.kakao.KakaoProfile;
import com.ysy.jwt.auth.model.naver.NaverProfile;
import com.ysy.jwt.auth.service.JwtService;
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
@RequestMapping("/ysy/v1/oauth")
//@RequiredArgsConstructor
public class OAuthController {

	
	@Autowired
	private JwtService authUtil;
	@Autowired
	private  AuthenticationManager authenticationManager;
	
	@Autowired
	private YsyUserMstService ysyUserMstService; 
	
	private static String OAUTH_PWD = "oauth_login_tmp_password";//Kakao login시 default password 변경안되게 처리하자. 
	
	
	private final String kakao_Content_type = "application/x-www-form-urlencoded;charset=utf-8";
	private final String kakao_client_id    = "4c9e081b17404f289741f6792bd4c6e7";
	private final String kakao_grant_type   = "authorization_code";
//	private final String kakao_redirect_uri = SystemConfig.SEVER_URL + "/ysy/v1/auth/kakao/redirect";
	private final String kakao_redirect_uri = "http://localhost:8080/kakaoLogin";
	private final String kakao_auth_addr    = "https://kauth.kakao.com/oauth/token";
	
	
	private final String bearer  = "Bearer ";
	private final String kakao_profile_url  = "https://kapi.kakao.com/v2/user/me";
	
	
	//비즈코드 default 등록 -> 비즈코드가 있는 곳에서는 회원가입을 자동으로 해주면 안되고 
	//원래 회원 정보를 각 kakao나 naver에 요청 후 추가정보를 입력받고 현 db에 저장해야 함.
	// 지금은 테스트로 그냥 바로 회원가입 진행중임.
	private final String tmp_biz_cd = "0001";
	
	
	@Data
	public static class OAuthCode{
		String code;
		String path ;
	}
	//naver 인증 콜백
	@PostMapping("/naver/setCode")
	public String naverSetCode(@RequestBody OAuthCode oAuthCode , HttpServletResponse response) {
		System.out.println("naverSetCode code = [" + oAuthCode.getCode() + "] path = ["  + oAuthCode.getPath() +"]");
		
		String token = getNaverToken(oAuthCode.getCode());
		NaverProfile naverProfile = getNaverProfile(token);
		
		JoinDto joinDto = JoinDto.builder()
				.bizCd(tmp_biz_cd)//카카오 로그인 전 비즈코드 받는 부분 필수.
				.username(naverProfile.getResponse().getEmail())
				.password(OAUTH_PWD)//default password -> 가입 후 변경 요망.
				.name(naverProfile.getResponse().getName())
				.oAuthPath("naver")//kakao or naver ....
				.build();
		String msg = oAuthUserSave(joinDto );
		
//		createClientToken(msg , joinDto, response);
		
		return createClientToken(msg , joinDto, response);
	}
	
	//get naver oAuth token 발급
	public String getNaverToken(String code) {
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", kakao_Content_type);
		
		MultiValueMap<String , String > params = new LinkedMultiValueMap<String ,String>();
		params.add("grant_type"   , kakao_grant_type);
		params.add("client_id"    , "75NEjj6MeqfW6we4eFlJ");
		params.add("client_secret", "uuaPVTTtVm");
		params.add("code"         , code);
		
		HttpEntity<MultiValueMap<String,String>> naverTokenReq = new HttpEntity<>(params,headers);
		ResponseEntity<String> response =  rt.exchange("https://nid.naver.com/oauth2.0/token?", HttpMethod.POST, naverTokenReq, String.class) ;
		
		
		System.out.println("getNaverToken response.getBody = "+response.getBody());
		
		ObjectMapper mapper = new ObjectMapper();
		OAuthTokenModel oToken = null;
		try {
			oToken = mapper.readValue(response.getBody(), OAuthTokenModel.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("oToken.getAccess_token() = "+oToken.getAccess_token());
		
		return oToken.getAccess_token();
	}
    
	//get naver profile 
	public NaverProfile getNaverProfile(String accessToken) {
		
		String naverProfileUrl = "https://openapi.naver.com/v1/nid/me";
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type" , "application/xml");
		headers.add("Authorization", bearer + accessToken);
		
		
		HttpEntity<MultiValueMap<String,String>> naverInfoReq = new HttpEntity<>(headers);
		
		ResponseEntity<String> response2 =  
				rt.exchange(naverProfileUrl, HttpMethod.POST, naverInfoReq, String.class) ;
		
		System.out.println("2.naverProfile response.getBody = "+response2.getBody());
		
		
		ObjectMapper mapper2 = new ObjectMapper();
		NaverProfile naverProfile = null;
		try {
			naverProfile = mapper2.readValue(response2.getBody(), NaverProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(naverProfile.getResponse().getEmail());
		System.out.println(naverProfile.getResponse().getMobile());
		System.out.println(naverProfile.getResponse().getName());
		
		return naverProfile;
	}
    
    
    
    
    
	
	
	
	
	
	//kakao 인증 콜백
	@PostMapping("/kakao/setCode")
	public String kakaoSetCode(@RequestBody OAuthCode kCode , HttpServletResponse response) {
		
		String access_token = getKakaoToken(kCode.getCode());
		
		KakaoProfile profile = getKakaoProfile(access_token);
		
		
		JoinDto joinDto = JoinDto.builder()
				.bizCd(tmp_biz_cd)//카카오 로그인 전 비즈코드 받는 부분 필수.
				.username(profile.getKakao_account().getEmail())
				.password(OAUTH_PWD)//default password -> 가입 후 변경 요망.
				.name(profile.getKakao_account().getProfile().getNickname())
				.oAuthPath("kakao")//kakao or naver ....
				.build();
		String msg = oAuthUserSave(joinDto );
		
		return createClientToken(msg , joinDto, response);
	}
	
	/* token 받기
	 * POST /oauth/token HTTP/1.1
		Host: kauth.kakao.com
		Content-type: application/x-www-form-urlencoded;charset=utf-8
		<<params>>kdska
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
	public String getKakaoToken(String code) {
		
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
		OAuthTokenModel oToken = null;
		try {
			oToken = mapper.readValue(response.getBody(), OAuthTokenModel.class);
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
		headers.add("Authorization", bearer + accessToken);
		
		
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
	public String oAuthUserSave(JoinDto joinDto ) {
		
		
		
		String msg = ysyUserMstService.signUp(joinDto);
		
		return msg;
	}
	
	
	
	
	
	
	
	/** client token resopnse */
	public String createClientToken(String msg , JoinDto joinDto , HttpServletResponse response) {
		
		if(msg.equals("ok")) {
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(
							joinDto.getUsername(),
							OAUTH_PWD); 
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			System.out.println("principalDetails.getUser().getUsername() = > "+principalDetails.getUser().getUsername());
//			SecurityContext context = SecurityContextHolder.createEmptyContext(); 
//			context.setAuthentication(authentication);
			
			
			String jwtToken   = authUtil.createJwtAccessToken(joinDto.getUsername(), joinDto.getName());
			String jwtTokenRe = authUtil.createJwtRefreshToken(joinDto.getUsername(), joinDto.getName());
			
			String tokenMsg = authUtil.tokenSend(response, jwtToken, jwtTokenRe);
			
//			String jwtToken = JWT.create()
//					.withSubject("jwtToken")
//					.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
//					.withClaim("name"    , joinDto.getName())
//					.withClaim("username", joinDto.getUsername())
//					.sign(Algorithm.HMAC512(JwtProperties.SECRET));
//			
//			String jwtTokenRe = JWT.create()
//					.withSubject("jwtToken")
//					.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME_RE))
//					.withClaim("name"    , joinDto.getName())
//					.withClaim("username", joinDto.getUsername())
//					.sign(Algorithm.HMAC512(JwtProperties.SECRET+"refresh"));
//			
//			response.addHeader(JwtProperties.HEADER_STRING  , JwtProperties.TOKEN_PREFIX+jwtToken);
//			response.addHeader(JwtProperties.HEADER_REFRESH , JwtProperties.TOKEN_PREFIX+jwtTokenRe);
//			response.addHeader("state","200");
			
			return tokenMsg;
		}
		
		return msg;
	}
	
	
	
}
