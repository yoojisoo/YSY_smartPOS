package com.ysy.jwt.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.ysy.jwt.auth.common.SysEnum;
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
	
	
	//정회원 (권한 : ROLE_USER ~ ROLE_ADMIN)
	private String user_roles   = "hasRole('"+SysEnum.enumGrps.ROLE_USER.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP1.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP2.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP3.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER_SUPER.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	
	//유료회원1 (권한 : ROLE_USER_VIP1 ~ ROLE_ADMIN)
	private String user1_roles  = "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP1.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP2.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP3.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER_SUPER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	
	//유료회원2 (권한 : ROLE_USER_VIP2 ~ ROLE_ADMIN)
	private String user2_roles  = "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP2.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP3.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER_SUPER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	
	//유료회원3 (권한 : ROLE_USER_VIP3 ~ ROLE_ADMIN)
	private String user3_roles  = "hasRole('"+SysEnum.enumGrps.ROLE_USER_VIP3.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER_SUPER.toString()+"') or "
			                    + "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	
	//매니저 (권한 : ROLE_MANAGER ~ ROLE_ADMIN)
	private String mng_roles    = "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_MANAGER_SUPER.toString()+"') or "
					            + "hasRole('"+SysEnum.enumGrps.ROLE_ADMIN.toString()+"')";
	
	//관리자 (권한 : ROLE_ADMIN)
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
		.antMatchers("/ysy/v1/user/**")     .access(user_roles)
		.antMatchers("/ysy/v1/user/vip1/**").access(user1_roles)
		.antMatchers("/ysy/v1/user/vip2/**").access(user2_roles)
		.antMatchers("/ysy/v1/user/vip3/**").access(user3_roles)
		.antMatchers("/ysy/v1/manager/**")  .access(mng_roles)
		.antMatchers("/ysy/v1/admin/**")    .access(admin_roles)
//		.antMatchers(PERMIT_URL_ARRAY).permitAll()
		.anyRequest().permitAll()
		;
		
	}
	
	private static final String[] PERMIT_URL_ARRAY = {
        /* swagger v3 */
        "/v3/api-docs",
        "/swagger-ui/**"
    };
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	
	
	
	
	
	
	
}
