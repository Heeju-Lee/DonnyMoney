package com.web.spring.dto;

import com.web.spring.entity.Child;
import com.web.spring.entity.Parent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResponseDto {
	
	private Long num;
	private String id;
	private String pwd;
	private String name;
	private String birthdate;
	private String phone;
	private String email;
//	private LocalDate modifiedAt;
//	private LocalDate createdAt;

	public SignInResponseDto(Child child) {
		this.num = child.getChildNum();
		this.id = child.getId();
		this.pwd = child.getPwd();
		this.name = child.getName();
		this.birthdate = child.getBirthdate();
		this.phone = child.getPhone();
		this.email = child.getEmail();
//		this.createdAt = child.getCreatedAt();
//		this.modifiedAt = child.getModifiedAt();

	}

	public SignInResponseDto(Parent parent ) {
		this.num = parent.getParentNum();
		this.id = parent.getId();
		this.pwd = parent.getPwd();
		this.name = parent.getName();
		this.birthdate = parent.getBirthdate();
		this.phone = parent.getPhone();
		this.email = parent.getEmail();
//		this.createdAt = parent.getCreatedAt();
//		this.modifiedAt = parent.getModifiedAt();
		
	}
	
	
}
