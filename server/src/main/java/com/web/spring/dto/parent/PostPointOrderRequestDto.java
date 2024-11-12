package com.web.spring.dto.parent;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
public class PostPointOrderRequestDto {

	int amount;
	Long childNum;
	String payType;
	
	public PointOrder toPointOrder(PostPointOrderRequestDto pointOrderRequestDto) {
		return PointOrder.builder()
						 .amount(pointOrderRequestDto.getAmount())
						 .childNum(pointOrderRequestDto.getChildNum())
						 .payType(pointOrderRequestDto.getPayType())
						 .build();
	}
	
}
