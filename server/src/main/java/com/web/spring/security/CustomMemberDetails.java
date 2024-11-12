package com.web.spring.security;

import java.util.ArrayList;
import java.util.Collection;

import com.web.spring.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomMemberDetails implements UserDetails{
	
	@Getter
	public final Member member;

	//Member값을 넣어줌
	public CustomMemberDetails(Member member) {
		this.member = member;
		log.info("CustomMemberDetails() ===> member { }",member);
	}
	
	//Role값을 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.info("getAuthorities() ===>");
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(()-> member.getRole());
		return collection;
	}
	
	@Override
	public String getPassword() {
		log.info("getPassword() ===>{}"+member.getPwd());
		return member.getPwd();
	}

	@Override
	public String getUsername() {
		log.info("getUsername() ===>{}"+member.getId());
		return member.getId();
	}

	
	public void setMemberNum(Long num) {
		 member.setMemberNum(num);
	}
	

	public Long getMemberNum() {
		 return member.getMemberNum();

	}
	
	////////////////////////////////////////////////////////
	@Override
    public boolean isAccountNonExpired() { //계정이 Expired 되지 않았다
        log.info("isAccountNonExpired...");
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //계정이 Lock되지 않았다.
        log.info("isAccountNonLocked...");
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        log.info("isCredentialsNonExpired...");
        return true;
    }

    @Override
    public boolean isEnabled() {
        log.info("isEnabled...");
        return true;
    }



}











