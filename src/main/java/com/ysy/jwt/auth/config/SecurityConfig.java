package com.ysy.jwt.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.ysy.common.SysEnum;
import com.ysy.jwt.auth.filter.JwtAuthenticationFilter;
import com.ysy.jwt.auth.filter.JwtAuthorizationFilter;
import com.ysy.jwt.auth.repository.YsyUserMstRepository;
import com.ysy.jwt.auth.service.JwtService;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CorsConfig corsConfig;
	
	@Autowired
	private YsyUserMstRepository ysyUserRepository;
	
	@Autowired
	private JwtService jwtUtil;
	
	
	//인증된 회원
	private String user_roles   = "hasRole('"+SysEnum.enumGrps.ROLE_USER.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP1.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP2.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER_SUPER.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	//vip1회원
	private String user1_roles  = "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP1.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP2.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER_SUPER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	//vip2회원
	private String user2_roles  = "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP2.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER_SUPER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	//관리자
	private String mng_roles    = "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER_SUPER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	
	private String admin_roles  = "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		System.out.println("user roles = " +user_roles);
		
		http
	    .csrf().disable()
	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//현재 서버는 stateless(서버에 세션 상태가 없는것) 타입의 session 정책
	    .and()
	    .formLogin().disable() // html form 로그인 사용 x
	    .httpBasic().disable() //
	    
	    .addFilter(corsConfig.corsFilter())
	    .addFilter(new JwtAuthenticationFilter(authenticationManager() , jwtUtil))
	    .addFilter(new JwtAuthorizationFilter(authenticationManager() ,ysyUserRepository , jwtUtil ))
	    .cors().and()
	    .authorizeRequests()
		.antMatchers("/ysy/v1/user/**")   .access(user_roles)
		.antMatchers("/ysy/v1/user/vip1/**")   .access(user1_roles)
		.antMatchers("/ysy/v1/user/vip2/**")   .access(user2_roles)
		.antMatchers("/ysy/v1/manager/**").access(mng_roles)
		.antMatchers("/ysy/v1/admin/**")  .access(admin_roles)
		.anyRequest().permitAll()
		;
		
	}
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	
	
	
	
	
	
	
}
