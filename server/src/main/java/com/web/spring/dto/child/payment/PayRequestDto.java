package com.web.spring.dto.child.payment;

import java.time.LocalDate;

import com.web.spring.dto.child.plan.PlanRequestDto;

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
public class PayRequestDto {

	@NotNull
	private Long childNum;

	@NotNull
	private int year;

	@NotNull
	private int month;


}
