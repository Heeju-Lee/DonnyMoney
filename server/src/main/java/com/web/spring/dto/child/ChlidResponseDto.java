package com.web.spring.dto.child;

import com.web.spring.entity.Child;

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
public class ChlidResponseDto {
	
	private Long childNum;
	private String id;
	private String pwd;
	private String name;
	private String birthdate;
	private String phone;
	private String email;
	private LocalDate modifiedAt;
	private LocalDate createdAt;

	
	public ChlidResponseDto(Child child) {
		this.childNum = child.getChildNum();
		this.id = child.getId();
		this.pwd = child.getPwd();
		this.name = child.getName();
		this.birthdate = child.getBirthdate();
		this.phone = child.getPhone();
		this.email = child.getEmail();
		this.createdAt = child.getCreatedAt();
		this.modifiedAt = child.getModifiedAt();

	}

	
}
