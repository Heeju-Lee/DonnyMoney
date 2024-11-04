package com.web.spring.dto.plan;

import java.time.LocalDate;

import com.web.spring.entity.Plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponseDto {
	
	private Long plan_num;
	private int shopping;
	private int transport;
	private int cvs;
	private int food;
	private int others;
	private int saving;
	private LocalDate date;
	
	public PlanResponseDto(Plan plan) {
	    this.plan_num = plan.getPlanNum();
	    this.shopping = plan.getShopping();
	    this.transport = plan.getTransport();
	    this.cvs = plan.getCvs();
	    this.food = plan.getFood();
	    this.others = plan.getOthers();
	    this.saving = plan.getSaving();
	    this.date = plan.getDate();
	}
	
}