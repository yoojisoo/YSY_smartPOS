package com.ysy.jwt.auth.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ysy.jwt.auth.entity.YsyUserMst;

/**
 * @author clubbboy@naver.com
 *  2022. 5. 5.
 *  Desc : 로그인 및 권한 처리시 사용하기 위한 class
 */
public class PrincipalDetails implements UserDetails{

	private YsyUserMst user;
	
	
	public PrincipalDetails(YsyUserMst user) {
		System.out.println("PrincipalDetails -> PrincipalDetails 생성자 실행");
		this.user = user;
	}
	
	public YsyUserMst getUser() {
		return user;
	}
	
	/** 권한을 관리하기 위한 함수 securityConfig의 hashRole("") => 여기 있는거랑 맵핑 되어야함. */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		System.out.println("PrincipalDetails -> getAuthorities 실행");
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		user.getRoleList().forEach(r -> {
        	authorities.add(()->{ return r;});
        });
		
		return authorities;

	}
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
