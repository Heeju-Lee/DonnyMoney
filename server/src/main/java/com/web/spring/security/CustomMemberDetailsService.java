package com.web.spring.security;

import com.web.spring.entity.Child;
import com.web.spring.entity.Member;
import com.web.spring.entity.Parent;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.spring.repository.ChildRepository;
import com.web.spring.repository.ParentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomMemberDetailsService implements UserDetailsService{

	private final ParentRepository parentRepository;
	private final ChildRepository childRepository;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsService loadUserByUsername...username :: {}", username); //id

        //Parent 테이블에서 조회 시도
        Member findMember = null;
        Parent findParent = parentRepository.findById(username);
        Child findChild = childRepository.findById(username);
        
        System.out.println("findParent : " + findParent);
        System.out.println("findChild : " + findChild);
        
        if (findParent == null) {
        	   findMember = Member.builder()
            			.id(findChild.getId())
            			.pwd(findChild.getPwd())
            			.role("ROLE_CHILD")
            			.name(findChild.getName())
            			.build();
        }
        else if (findChild == null) {
        	  findMember = Member.builder()
            			.id(findParent.getId())
            			.pwd(findParent.getPwd())
            			.role("ROLE_PARENT")
            			.name(findParent.getName())
            			.build();
        }
        // 둘 다 null일 경우 UsernameNotFoundException 발생
        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }


        
        log.info("findMember...찾았다!! :: {}", findMember);
        return new CustomMemberDetails(findMember);
    }

}

















