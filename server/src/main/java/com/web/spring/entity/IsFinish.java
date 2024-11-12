package com.web.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsFinish {

	INCOMPLETE("미완"),
	COMPLETE("완료");

	
	private String isFinish;
}
