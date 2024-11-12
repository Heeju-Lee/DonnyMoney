package com.web.spring.dto;

import com.web.spring.entity.Child;
import com.web.spring.entity.Parent;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDto {
	
	@NotNull
	private String id;
	
	@NotNull
	private String pwd;
	
	@NotNull
	private String name;
	
	@NotNull
	private String birthdate;
	
	private String phone;
	private String email;
	private Long parentNum;
	
	public Child toChild(SignUpRequestDto requestDto) {
		
		return Child.builder()
				.id(requestDto.getId())
				.pwd(requestDto.getPwd())
				.name(requestDto.getName())
				.birthdate(requestDto.getBirthdate())
				.phone(requestDto.getPhone())
				.email(requestDto.getEmail())
				.build();
	}

	public Parent toParent(SignUpRequestDto requestDto) {
		
		return Parent.builder()
				.id(requestDto.getId())
				.pwd(requestDto.getPwd())
				.name(requestDto.getName())
				.birthdate(requestDto.getBirthdate())
				.phone(requestDto.getPhone())
				.email(requestDto.getEmail())
				.build();
	}

}
