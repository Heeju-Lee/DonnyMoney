package com.web.spring.dto.parent;

import com.web.spring.entity.IsFinish;
import com.web.spring.entity.Parent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParentResponeseDto {

	private Long parentNum;
	
	private String name;
	
	private String id;
	
	
	public ParentResponeseDto(Parent parent) {
		this.name = parent.getName();
		this.parentNum = parent.getParentNum();
		this.id = parent.getId();
	}
}
