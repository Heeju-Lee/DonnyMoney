package com.web.spring.dto.child.point;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointRequestDto {

	@NotNull
	private Long childNum;

	@NotNull
	private int point;
}
