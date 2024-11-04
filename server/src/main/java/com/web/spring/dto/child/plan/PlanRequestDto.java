package com.web.spring.dto.child.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

import com.web.spring.entity.Child;
import com.web.spring.entity.Plan;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanRequestDto {
	
	@NotNull
	private Long childNum;
	
	@NotNull
	private int shopping;
	
	@NotNull
	private int transport;
	
	@NotNull
	private int cvs;
	
	@NotNull
	private int food;
	
	@NotNull
	private int others;
	
	@NotNull
	private int saving;
	
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//	private LocalDate date;
	
	public Plan toPlan(PlanRequestDto planResponseDto) {
		
		return Plan.builder()
					//.child(Child.builder().childNum(childNum).build())
					.shopping(planResponseDto.getShopping())
					.transport(planResponseDto.getTransport())
					.cvs(planResponseDto.getCvs())
					.food(planResponseDto.getFood())
					.others(planResponseDto.getOthers())
					.saving(planResponseDto.getSaving())
					//.date(planResponseDto.getDate())
					.build();
	}

//	@Override
//	public String toString() {
//		return "PlanRequestDto [cNum=" + childNum + ", shopping=" + shopping + ", transport=" + transport + ", cvs=" + cvs
//				+ ", food=" + food + ", others=" + others + ", saving=" + saving + ", date=" + date + "]";
//	}
}