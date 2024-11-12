package com.web.spring.dto.parent;

import com.web.spring.entity.PointOrder;

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
public class PointOrderResponseDto {

	Long pointOrderNum;
	int amount;
	Long childNum;
	String payType;
	
	public PointOrderResponseDto(PointOrder pointOrder) {
		this.pointOrderNum = pointOrder.getOrderNum();
		this.amount = pointOrder.getAmount();
		this.childNum = pointOrder.getChildNum();
		this.payType = pointOrder.getPayType();
	}
	
}
