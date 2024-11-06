package com.web.spring.dto.child;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.spring.dto.child.plan.PlanRequestDto;
import com.web.spring.entity.Child;

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
public class ChildRequestDto {
	
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

	@NotNull
	private Long parentNum;
	
	public Child toChild(ChildRequestDto childRequestDto) {
		
		return Child.builder()
				.id(childRequestDto.getId())
				.pwd(childRequestDto.getPwd())
				.name(childRequestDto.getName())
				.birthdate(childRequestDto.getBirthdate())
				.phone(childRequestDto.getPhone())
				.email(childRequestDto.getEmail())
				.build();
	}

}
