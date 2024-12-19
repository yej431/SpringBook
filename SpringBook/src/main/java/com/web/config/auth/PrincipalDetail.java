package com.web.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.web.model.User;

import lombok.Getter;

//스프링 시큐리티의 고유한 세션저장소에 저장.
@Getter
public class PrincipalDetail implements UserDetails{
	private static final long serialVersionUID = 1L;
	private User user;
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}

	//계정이 만료되지 않았는지 리턴(true: 만료 안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠기지 않았는지 리턴(true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되지 않았는지 리턴(true: 만료 안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화(사용가능)인지 리턴(true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	} 
	
	//계정이 갖고있는 권한 목록 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->{return "ROLE_"+user.getRole();});
		return collectors;
	}

}
