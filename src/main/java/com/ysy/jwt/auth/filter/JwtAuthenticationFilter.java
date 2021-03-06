package com.ysy.jwt.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysy.jwt.auth.dto.JoinDto;
import com.ysy.jwt.auth.model.LoginReqData;
import com.ysy.jwt.auth.model.PrincipalDetails;
import com.ysy.jwt.auth.service.JwtService;

/**
 * @author clubbboy@naver.com
 *  2022. 5. 5.
 *  Desc : spring boot login시 PrincipalDetailsService의 서비스가 자동으로 실행됨 
 *         그럼 해당클래스의 loadUserByUsername 함수가 실행됨.
 *         그러나 formLogin을 안쓴다고 해놔서 로그인시 해당 메서드를 강제로 실행해 줘야함.
 *         jwt에 UsernamePasswordAuthenticationFilter 필턱가 /login 으로 호출시  실행됨.
 *         그러면 attemptAuthentication() 함수 호출해서 이 함수에서 로그인 인증 진행하면 됨.
 *         로그인 인증이 완료 되면 successfulAuthentication() 함수를 호출해주므로 
 *         해당 지점에서 token생성 후 response -> header에 담아주면 끝
 *         
 *         ** 추가설명 
 *         security config에 해당 class filter로 등록한다.                                                                          
 *         UsernamePasswordAuthenticationFilter 필터는 로그인 진행하는 필터임.                                                  
 *         필터등록시 authenticationManager을 인자로 받아야함.                                                                  
 *         authenticationManager 객체는 WebSecurityConfigurerAdapter 클래스에 있어서 
 *         securityConfig에서 등록시 인자로 던질 수 있음.       
 *         @RequiredArgsConstructor이것을 사용하면 생성자를 만들어서 받을 필요 없음                                                                 
 *         authenticationManager로 로그인 시도를 하면 principalDetailService가 호출이됨. 
 *         즉 loadUserByUsername 해당 함수가 호출됨.        
 *         그러면 pricipalDetail을 세션에 담는데 이것은 권한관리를 위한것이기 때문에 권한관리를 안할꺼면 안담아도됨                                        
 *         
 *         ** 처리안된것
 *         - token만료시간 처리로 인해서 2개의 토근을 생성하고 하나는 인증용으로 만료시간을 짧게 생성
 *           하나는 토근 갱신용으로 인증시간을 길게 생성해서 클라인언트에서 요청시 토근 갱신해서 넘겨주면 댐. 
 */
//@RequiredArgsConstructor//해당 클래스를 필터로 등록시 인자로 받기위해 어노테이션 선언
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	
	private  AuthenticationManager authenticationManager;
	
	
	private  JwtService jwtUtil;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager , JwtService jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	/*/login 요청시 실행되는 함수 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("JwtAuthenticationFilter class -> attemptAuthentication() 진입");
		ObjectMapper om = new ObjectMapper();
		LoginReqData loginReqData = null;
		
		try {
			loginReqData = om.readValue(request.getInputStream() , LoginReqData.class);
		
		System.out.println("logdata ===> "+loginReqData);
		
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(
						loginReqData.getUsername(),
						loginReqData.getPassword()); 
		
		System.out.println("token create  ===> "+ authenticationToken);
		
		//loadUserByUsername 함수 호출
		Authentication authentication = 
				authenticationManager.authenticate(authenticationToken);
		
		//권한 처리때문에 생성함. session에 등록됨. 등록 안하면 권한 처리 할 수 없음. 
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		
		System.out.println("Authentication : "+principalDetails.getUser().getUsername());
		
		return authentication;
		} catch (IOException e) {
			System.out.println("123456");
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response
			, FilterChain chain
			, Authentication authResult) throws IOException, ServletException {
		
		System.out.println("JwtAuthenticationFilter class --->  successfulAuthentication 진입 ");
		
		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
		
		String jwtToken   = jwtUtil.createJwtAccessToken(principalDetails.getUser().getUsername(), principalDetails.getUser().getName());
		String jwtTokenRe = jwtUtil.createJwtRefreshToken(principalDetails.getUser().getUsername(), principalDetails.getUser().getName());
		
		JoinDto dto = JoinDto
				.builder()
				.name(principalDetails.getUser().getUsername())
				.oAuthPath("")
				.bizCd(principalDetails.getUser().getYsyGrpMst().getGrpPK().getBizCd())
				.build();
		
		jwtUtil.tokenSend(response, jwtToken, jwtTokenRe);
		
	}
	
	
	
	

}
