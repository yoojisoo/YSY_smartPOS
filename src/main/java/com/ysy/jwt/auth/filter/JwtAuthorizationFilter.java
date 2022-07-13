package com.ysy.jwt.auth.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ysy.jwt.auth.dto.ResponseAuthDto;
import com.ysy.jwt.auth.entity.YsyUserMst;
import com.ysy.jwt.auth.model.PrincipalDetails;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;
import com.ysy.jwt.auth.service.JwtService;


/**
 * @author clubbboy@naver.com
 *  2022. 5. 5.
 *  Desc : 로그인 후 token을 들고오면 인증된 user인지 확인
 *         BasicAuthenticationFilter는 권한이나 인증이 필요한 주소를 요청할때 필터 동작함
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	
	private YsyUserMstRepository ysyUserRepository;

	private JwtService jwtService;
	
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager
			, YsyUserMstRepository ysyUserRepository
			, JwtService jwtService) 
	{
		super(authenticationManager);
		System.out.println("JwtAuthorizationFilter class JwtAuthorizationFilter 생성자 진입");
		this.ysyUserRepository = ysyUserRepository;
		this.jwtService = jwtService;
	}
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("JwtAuthorizationFilter class doFilterInternal  진입");
		
		ResponseAuthDto<String> resDto = new ResponseAuthDto<String>();
		
		
		String header = request.getHeader(jwtService.HEADER_STRING);
//		printPostData(request);
		
		
		
		if(header == null || !header.startsWith(jwtService.TOKEN_PREFIX)) {//내가 보낸 해더인지 검사
			chain.doFilter(request, response);
			return;
		}
	
		
		System.out.println("header : "+header); 
		
		String token = request.getHeader(jwtService.HEADER_STRING).replace(jwtService.TOKEN_PREFIX, "");
		String username  = jwtService.getClaim(token, "username" );
		String     name  = jwtService.getClaim(token, "name"     );
		String tokenType = jwtService.getClaim(token, "tokenType");
		
		if(username != null) {	
			System.out.println("doFilterInternal username==========>" + username);
			YsyUserMst user = ysyUserRepository.findById(username).orElse(null);
			
			if(user == null || user.getUsername().isEmpty()) {
//				chain.doFilter(request, response);
				response.addHeader("token_error"  , "["+username+"]:user not found.");
				
				return;
			}
			
			if(tokenType != null && tokenType.toUpperCase().indexOf("ACCESS") > -1) {
				
				if(jwtService.tokenExpirationCheck(token)) 
				{//만료시간 지남 : 만료 message 
					response.addHeader("token_error"  , "["+username+"]Access Token expiration");
					
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					PrintWriter writer = response.getWriter();
					writer.println("Access Token 만료됨");
					
					return;
				}
			}
			else 
			if(tokenType != null && tokenType.toUpperCase().indexOf("REFRESH") > -1) {
				
				String reToken = request.getHeader(jwtService.HEADER_REFRESH).replace(jwtService.TOKEN_PREFIX, "");
				//refresh token은 db 정보 가져와서 같은지 비교 후 처리해야함.
				if(!jwtService.getRefershToken(username).equals(reToken)) {
					response.addHeader("token_error"  , "["+username+"]Refresh Token expiration. go to login page!");
					return;
				}
				
				
				if(jwtService.tokenExpirationCheck(reToken)) 
				{//만료시간 지남 : 만료 message 
					response.addHeader("token_error"  , "["+username+"]Refresh Token expiration! go to login page!");
					return;
				}
				//refresh token 확인 후 access token 재발급
				String accessToken = jwtService.createJwtAccessToken(username,name);
				jwtService.tokenSend(response , accessToken , reToken);
				return;
			}
			
			// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해 
			// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
							                                    principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
							                                    null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
							                                    principalDetails.getAuthorities());
			
			// 강제로 시큐리티의 세션에 접근하여 값 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
			/** 이 부분에서 해당 token의 만료시간 체크하여 다시 response에 담아줌.
			 *  refresh token을 가지고 오면 처리해도 됨.
			 *  아무튼 예외처리 할 부분을 좀 있음 . */
		}
		
	}
	
	

}
